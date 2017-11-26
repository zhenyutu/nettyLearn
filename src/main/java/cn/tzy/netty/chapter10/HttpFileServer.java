package cn.tzy.netty.chapter10;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by tuzhenyu on 17-10-30.
 * @author tuzhenyu
 */
public class HttpFileServer {
    private static final String DEFAULT_URL = "/src/";

    private void run(final int port,final String url){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b= new ServerBootstrap();
            b.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            socketChannel.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
                            socketChannel.pipeline().addLast("http-encoder",new HttpResponseEncoder());
                            socketChannel.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
                            socketChannel.pipeline().addLast("fileServerHandler",new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new HttpFileServer().run(8000,DEFAULT_URL);
    }
}
