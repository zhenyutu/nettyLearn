package cn.tzy.netty.serverClient.client;

import cn.tzy.netty.serverClient.comment.Constants;
import cn.tzy.netty.serverClient.comment.Protocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Created by tuzhenyu on 18-4-19.
 * @author tuzhenyu
 */
public abstract class NettyConnector implements Connector{

    private Bootstrap bootstrap;
    private NioEventLoopGroup work;
    private int nWorks;

    private Protocol protocol;

    public NettyConnector(){
        this(Protocol.TCP, Constants.AVAILABLE_PROCESSORS_NUM << 1);
    }

    public NettyConnector(int nWorks){
        this(Protocol.TCP,nWorks);
    }

    public NettyConnector(Protocol protocol,int nWorks){
        this.protocol = protocol;
        this.nWorks = nWorks;
    }

    protected void init(){
        work = new NioEventLoopGroup(nWorks);
        bootstrap = new Bootstrap().group(work);

        initChannelFactory();
    }

    public void shutdownGracefully() {
        work.shutdownGracefully();
    }

    public Bootstrap bootstrap(){
        return bootstrap;
    }

    protected abstract void initChannelFactory();

}
