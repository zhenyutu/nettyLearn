package cn.tzy.chapter0;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * Created by tuzhenyu on 17-11-16.
 * @author tuzhenyu
 */
public class Test {
    public static void main(String[] args) {
//        ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer(1024);
//        buf.writeBytes("hello".getBytes());

        PooledByteBufAllocator p = new PooledByteBufAllocator(false);
        ByteBuf buf1 = p.buffer(1024);
        buf1.writeBytes("world".getBytes());
    }
}
