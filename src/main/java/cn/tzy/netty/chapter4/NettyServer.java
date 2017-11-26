package cn.tzy.netty.chapter4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by tuzhenyu on 17-10-29.
 * @author tuzhenyu
 */
public class NettyServer {
    private void bind(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
//                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    });
            ChannelFuture c = b.bind(port).sync();
            c.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyServer().bind(8000);
    }
}

class ServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String body = new String(bytes,"UTF-8");
//        String body = (String)msg;
        System.out.println("the client says: "+ body);

        byte[] req = null;
        ByteBuf buffer = null;
//        for (int i=0;i<100;i++){
//            req = ("this is No."+i+" server sent the message").getBytes();
//            buffer = Unpooled.buffer(req.length);
//            buffer.writeBytes(req);
//            ctx.writeAndFlush(buffer);
//        }

        for (int i=0;i<100;i++){
            req = ("hello world").getBytes();
            buffer = Unpooled.buffer(req.length);
            buffer.writeBytes(req);
            ctx.writeAndFlush(buffer);
        }

//        byte[] req = null;
//        ByteBuf buffer = null;
//        StringBuilder sb = new StringBuilder();
//        for (int i=0;i<100;i++){
//            sb.append("abcdefghijklmnopqrstuvwxyz");
//        }
//        sb.append("\n");
//        req = sb.toString().getBytes();
//        buffer = Unpooled.buffer(req.length);
//        buffer.writeBytes(req);
//        ctx.writeAndFlush(buffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
