package cn.tzy.netty.serverClient.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by tuzhenyu on 18-4-21.
 * @author tuzhenyu
 */
public class AcceptorIdleStateTrigger extends ChannelInboundHandlerAdapter{
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event==IdleStateEvent.READER_IDLE_STATE_EVENT){
                System.out.println("acceptor read idle");
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
