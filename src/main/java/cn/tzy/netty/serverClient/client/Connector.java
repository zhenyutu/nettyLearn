package cn.tzy.netty.serverClient.client;

import io.netty.channel.ChannelFuture;

import java.net.SocketAddress;

/**
 * Created by tuzhenyu on 18-4-19.
 * @author tuzhenyu
 */
public interface Connector {
    ChannelFuture connect(String ip,int port);

    void shutdownGracefully();
}
