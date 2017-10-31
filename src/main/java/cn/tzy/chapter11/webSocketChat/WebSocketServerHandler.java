/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.tzy.chapter11.webSocketChat;

import cn.tzy.chapter11.webSocketChat.entry.Operation;
import cn.tzy.chapter11.webSocketChat.entry.Request;
import cn.tzy.chapter11.webSocketChat.entry.Response;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;


import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

	private WebSocketServerHandshaker handshaker;
	private String sessionId;
    private volatile boolean stop = false;

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg)
	    throws Exception {
	// 传统的HTTP接入,用于WebSocket建立连接时HTTP握手
	if (msg instanceof FullHttpRequest) {
	    handleHttpRequest(ctx, (FullHttpRequest) msg);
	}
	// WebSocket接入，用于建立连接后的相互通信
	else if (msg instanceof WebSocketFrame) {
	    handleWebSocketFrame(ctx, (WebSocketFrame) msg);
	}
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx,FullHttpRequest req) throws Exception {
		//判断是否为握手HTTP报文，如果HTTP解码失败，返回HHTP异常
		if (!req.getDecoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1,BAD_REQUEST));
			return;
		}

		// 构造握手响应返回，本机测试
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				"ws://" + req.headers().get(HttpHeaders.Names.HOST), null, false);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory
				.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame) {

		// 判断是否是关闭链路的指令
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(),
				(CloseWebSocketFrame) frame.retain());
			return;
		}
		// 判断是否是Ping消息
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(
				new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		// 本例程仅支持文本消息，不支持二进制消息
		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format(
				"%s frame types not supported", frame.getClass().getName()));
		}

		try{
			System.out.println(((TextWebSocketFrame)frame).text());
			Request req = Request.create(((TextWebSocketFrame)frame).text());
			System.out.println(req.getMessage());
			Response response = new Response();
			response.setServiceId(req.getServiceId());
			if (Operation.ONLINE.getCode()==req.getServiceId()){
				String requestId = req.getRequestId();
				if (ChatService.connectionMap.containsKey(requestId)){
					response.setIsSucc(false);
					response.setMessage("您已经注册了，不能重复注册");
					sendWebSocket(JSON.toJSONString(response),ctx);
					return;
				}else {
					ChatService.register(req.getRequestId(),ctx);
					response.setIsSucc(true);
					response.setMessage("注册成功");
					sendWebSocket(JSON.toJSONString(response),ctx);
					Response res = new Response();
					res.setIsSucc(true);
					res.setServiceId(Operation.ONLINE.getCode());
					res.setMessage(req.getName()+"注册成功");
					for (String reqId : ChatService.connectionMap.keySet()){
						if (!req.getRequestId().equals(reqId))
							sendWebSocket(JSON.toJSONString(res),ChatService.connectionMap.get(reqId));
					}
				}
			}else if (Operation.MESSAGE.getCode()==req.getServiceId()){
				Response res = new Response();
				res.setIsSucc(true);
				res.setServiceId(Operation.MESSAGE.getCode());
				res.setMessage(req.getName()+":"+req.getMessage());
				for (String reqId : ChatService.connectionMap.keySet()){
					sendWebSocket(JSON.toJSONString(res),ChatService.connectionMap.get(reqId));
				}
			}else if (Operation.OUTLINE.getCode()==req.getServiceId()){
				ChatService.logout(req.getRequestId());

				Response res = new Response();
				res.setIsSucc(true);
				res.setServiceId(Operation.MESSAGE.getCode());
				res.setMessage(req.getName()+"下线成功");
				for (String reqId : ChatService.connectionMap.keySet()){
					sendWebSocket(JSON.toJSONString(res),ChatService.connectionMap.get(reqId));
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, FullHttpResponse res) {
	// 返回应答给客户端
		if (res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
				CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			setContentLength(res, res.content().readableBytes());
		}

		// 如果是非Keep-Alive，关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!isKeepAlive(req) || res.getStatus().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
    }

	public void sendWebSocket(String msg,ChannelHandlerContext ctx) throws Exception {
		if ( ctx == null || ctx.isRemoved()) {
			throw new Exception("尚未握手成功，无法向客户端发送WebSocket消息");
		}
		ctx.channel().write(new TextWebSocketFrame(msg));
		ctx.flush();
	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	    throws Exception {
	cause.printStackTrace();
	ctx.close();
    }
}
