package cn.tzy.netty.chapter14;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Created by tuzhenyu on 17-11-19.
 * @author tuzhenyu
 */
public class NettyClient {
    private void connect(String host,int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("encoder", new LengthFieldPrepender(4,false));
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });

            ChannelFuture f = b.connect(host,port).sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        client.connect("127.0.0.1",8008);
    }
}

class ClientHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] req = null;
        ByteBuf buffer = null;
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<100;i++){
            sb.append("abcdefghijklmnopqrstuvwxyz");
        }
        req = sb.toString().getBytes();
        buffer = Unpooled.buffer(req.length);
        buffer.writeBytes(req);
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}