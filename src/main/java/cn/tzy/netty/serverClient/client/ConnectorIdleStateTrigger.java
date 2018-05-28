package cn.tzy.netty.serverClient.client;

import cn.tzy.netty.serverClient.commont.HeartBeat;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by tuzhenyu on 18-4-21.
 * @author tuzhenyu
 */
public class ConnectorIdleStateTrigger extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state() == IdleState.WRITER_IDLE){
                System.out.println("send heart beat to server");
                ctx.writeAndFlush(HeartBeat.heartBeatContent());
            }
        }else
            super.userEventTriggered(ctx, evt);
    }
}
