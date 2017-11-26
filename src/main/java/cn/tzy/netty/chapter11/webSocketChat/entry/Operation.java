package cn.tzy.netty.chapter11.webSocketChat.entry;

/**
 * Created by tuzhenyu on 17-10-31.
 * @author tuzhenyu
 */
public enum Operation {
    ONLINE(1001),MESSAGE(1002),OUTLINE(1003);

    private int code;
    private Operation(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return ""+this.code;
    }
}
