package cn.tzy.netty.serverClient.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.net.SocketAddress;

/**
 * Created by tuzhenyu on 18-4-19.
 * @author tuzhenyu
 */
public class NettyClient extends NettyTcpConnector{
    public ChannelFuture connect(String ip, int port) {
        final Bootstrap bootstrap = bootstrap();

        bootstrap.handler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel ch) throws Exception {
                final ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new TimeClientHandler());
            }
        });

        ChannelFuture future = null;
        try{
            future = bootstrap.connect(ip, port).sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return future;
    }

    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        ChannelFuture future = client.connect("127.0.0.1",8008);
        try {
            future.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] bytes = "QUERY TIME ORDER".getBytes();
        ByteBuf buf = Unpooled.buffer(bytes.length);
        buf.writeBytes(bytes);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String body = new String(bytes,"UTF-8");
        System.out.println("the server says: "+ body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
