package cn.tzy.netty.serverClient.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

import java.net.SocketAddress;

/**
 * Created by tuzhenyu on 18-4-19.
 * @author tuzhenyu
 */
public class NettyServer extends NettyTcpAcceptor{
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

            }
        });

        return bootstrap.bind(localAddress);
    }
}
