package cn.tzy.netty.chapter12;

/**
 * Created by tuzhenyu on 17-11-1.
 * @author tuzhenyu
 */
public class NettyMessage {
    private Header header;
    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
