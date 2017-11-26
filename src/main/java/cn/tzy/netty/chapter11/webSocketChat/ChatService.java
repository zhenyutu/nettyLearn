package cn.tzy.netty.chapter11.webSocketChat;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tuzhenyu on 17-10-31.
 * @author tuzhenyu
 */
public class ChatService {
    public static final Map<String, ChannelHandlerContext> connectionMap = new ConcurrentHashMap<String,ChannelHandlerContext>();

    public static void register(String requestId, ChannelHandlerContext ctx){
        connectionMap.put(requestId,ctx);
    }

    public static void logout(String requestId){
        connectionMap.remove(requestId);
    }
}
