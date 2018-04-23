package cn.tzy.netty.serverClient.server;

import cn.tzy.netty.serverClient.comment.Protocol;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by tuzhenyu on 18-4-19.
 * @author tuzhenyu
 */
public abstract class NettyTcpAcceptor extends NettyAcceptor{

    public NettyTcpAcceptor(int port){
        super(Protocol.TCP,new InetSocketAddress(port));
        init();
    }
    public NettyTcpAcceptor(SocketAddress address) {
        super(Protocol.TCP,address);
        init();
    }

    @Override
    protected void initChannelFactory() {
        bootstrap().channel(NioServerSocketChannel.class);
    }
}
