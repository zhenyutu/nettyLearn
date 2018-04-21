package cn.tzy.netty.serverClient.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.net.SocketAddress;
import java.util.Date;

/**
 * Created by tuzhenyu on 18-4-19.
 * @author tuzhenyu
 */
public class NettyServer extends NettyTcpAcceptor{
    public NettyServer(int port){
        super(port);
    }

    public NettyServer(SocketAddress address) {
        super(address);
    }

    @Override
    protected ChannelFuture bind(SocketAddress localAddress) {
        ServerBootstrap bootstrap = bootstrap();

        bootstrap.childHandler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline =ch.pipeline();
                pipeline.addLast(new TimeServerHandler());
            }
        });

        return bootstrap.bind(localAddress);
    }

    public static void main(String[] args) {
        NettyServer server = new NettyServer(8008);

        try {
            server.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}

class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String body = new String(bytes,"UTF-8");
        System.out.println("the client says: "+ body);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new Date().toString():"BAD ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
