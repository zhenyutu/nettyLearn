package cn.tzy.chapter12;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by tuzhenyu on 17-11-1.
 * @author tuzhenyu
 */
public class NettyClient {
    private void connect(String ip,int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b= new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                            socketChannel.pipeline().addLast(new NettyMessageEncoder());
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {

    }
}
