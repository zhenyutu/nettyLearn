package cn.tzy.netty.serverClient.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.SocketAddress;

/**
 * Created by tuzhenyu on 18-4-19.
 * @author tuzhenyu
 */
public abstract class NettyTcpConnector extends NettyConnector{

    public NettyTcpConnector(){
        init();
    }

    @Override
    protected void initChannelFactory() {
        bootstrap().channel(NioSocketChannel.class);
    }

}
