package cn.tzy.netty.serverClient.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

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
}
