package cn.tzy.netty.serverClient.comment;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static cn.tzy.netty.serverClient.comment.MessageProtocol.HEAD_LENGTH;
import static cn.tzy.netty.serverClient.comment.MessageProtocol.HEART_BEAT;
import static cn.tzy.netty.serverClient.comment.MessageProtocol.MAGIC;

/**
 * Created by tuzhenyu on 18-4-23.
 * @author tuzhenyu
 */
public class HeartBeat {
    private static final ByteBuf HEART_BEAT_BUF;

    static {
        ByteBuf buf = Unpooled.buffer(HEAD_LENGTH);
        buf.writeShort(MAGIC);
        buf.writeByte(HEART_BEAT);
        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(0);

        HEART_BEAT_BUF = Unpooled.unreleasableBuffer(buf).asReadOnly();
    }

    public static ByteBuf heartBeatContent(){
        return HEART_BEAT_BUF.duplicate();
    }
}
