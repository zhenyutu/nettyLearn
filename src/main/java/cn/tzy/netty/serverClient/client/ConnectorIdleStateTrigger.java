package cn.tzy.netty.serverClient.client;

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
                ctx.writeAndFlush("biubiu");
            }
        }else
            super.userEventTriggered(ctx, evt);
    }
}
