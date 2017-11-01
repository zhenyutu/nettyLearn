
package cn.tzy.chapter12;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;

import java.io.IOException;

class ChannelBufferByteOutput implements ByteOutput {

    private final ByteBuf buffer;

    public ChannelBufferByteOutput(ByteBuf buffer) {
        this.buffer = buffer;
    }

    public void close() throws IOException {
        // Nothing to do
    }

    public void flush() throws IOException {
        // nothing to do
    }

    public void write(int b) throws IOException {
        buffer.writeByte(b);
    }

    public void write(byte[] bytes) throws IOException {
        buffer.writeBytes(bytes);
    }

    public void write(byte[] bytes, int srcIndex, int length) throws IOException {
        buffer.writeBytes(bytes, srcIndex, length);
    }

    ByteBuf getBuffer() {
        return buffer;
    }
}
