package cn.tzy.netty.serverClient.server;

import java.net.SocketAddress;

/**
 * Created by tuzhenyu on 18-4-19.
 * @author tuzhenyu
 */
public interface Acceptor {
    SocketAddress localAddress();

    void start() throws InterruptedException;

    void shutdownGracefully();
}
