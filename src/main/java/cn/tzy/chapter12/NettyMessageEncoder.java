package cn.tzy.chapter12;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tuzhenyu on 17-11-1.
 * @author tuzhenyu
 */
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage>{

    private MarshallingEncoder marshallingEncoder;
    {
        try{
            marshallingEncoder = new MarshallingEncoder();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage nettyMessage, ByteBuf byteBuf) throws Exception {
        if (nettyMessage==null||nettyMessage.getHeader()==null)
            throw new Exception("The encode message is null");
        byteBuf.writeInt(nettyMessage.getHeader().getCrcCode());
        byteBuf.writeInt(nettyMessage.getHeader().getLength());
        byteBuf.writeLong(nettyMessage.getHeader().getSessionID());
        byteBuf.writeByte(nettyMessage.getHeader().getType());
        byteBuf.writeByte(nettyMessage.getHeader().getPriority());
        byteBuf.writeInt(nettyMessage.getHeader().getAttachment().size());

        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String,Object> param : nettyMessage.getHeader().getAttachment().entrySet()){
            key = param.getKey();
            keyArray = key.getBytes("UTF-8");
            byteBuf.writeInt(keyArray.length);
            byteBuf.writeBytes(keyArray);
            value = param.getValue();
            marshallingEncoder.encode(value,byteBuf);
        }

        key = null;
        keyArray = null;
        value = null;

        if (nettyMessage.getBody()!=null){
            marshallingEncoder.encode(nettyMessage.getBody(),byteBuf);
        }else {
            byteBuf.writeInt(0);
        }
        byteBuf.setInt(4,byteBuf.readableBytes());
    }
}
