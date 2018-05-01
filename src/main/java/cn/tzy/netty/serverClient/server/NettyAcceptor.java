package cn.tzy.netty.serverClient.server;

import cn.tzy.netty.serverClient.comment.Constants;
import cn.tzy.netty.serverClient.comment.TransportTypeProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.SocketAddress;

/**
 * Created by tuzhenyu on 18-4-19.
 * @author tuzhenyu
 */
public abstract class NettyAcceptor implements Acceptor{

    private ServerBootstrap bootstrap;
    private NioEventLoopGroup boss;
    private NioEventLoopGroup work;

    private int nWorks;

    private SocketAddress address;
    private TransportTypeProtocol protocol;

    public NettyAcceptor(TransportTypeProtocol protocol, SocketAddress address){
        this(protocol,address, Constants.AVAILABLE_PROCESSORS_NUM << 1);
    }

    public NettyAcceptor(TransportTypeProtocol protocol, SocketAddress address, int nWorks){
        this.protocol = protocol;
        this.address = address;
        this.nWorks = nWorks;
    }

    protected void init(){
        boss = new NioEventLoopGroup(1);
        work = new NioEventLoopGroup(nWorks);

        bootstrap = new ServerBootstrap().group(boss,work);

        initChannelFactory();
    }

    public SocketAddress localAddress() {
        return address;
    }

    public void start() throws InterruptedException {
        start(true);
    }

    private void start(boolean sync)throws InterruptedException{
        ChannelFuture future = bind(address).sync();
        if (sync){
            future.channel().closeFuture().sync();
        }
    }

    public void shutdownGracefully() {
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }

    protected ServerBootstrap bootstrap(){
        return bootstrap;
    }

    protected abstract ChannelFuture bind(SocketAddress localAddress);

    protected abstract void initChannelFactory();


}
