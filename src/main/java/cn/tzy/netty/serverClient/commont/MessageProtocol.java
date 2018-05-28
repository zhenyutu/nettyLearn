package cn.tzy.netty.serverClient.commont;

/**
 * Created by tuzhenyu on 18-4-23.
 * @author tuzhenyu
 */

/***************************************************************************************************
        *                                          Protocol
        *  ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐
        *       2   │   1   │    1   │     8     │      4      │
        *  ├ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┤
        *           │       │        │           │             │
        *  │  MAGIC   Sign    Status   Invoke Id   Body Length                   Body Content              │
        *           │       │        │           │             │
        *  └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
        *
        * 消息头16个字节定长
        * = 2 // MAGIC = (short) 0xbabe
        * + 1 // 消息标志位, 用来表示消息类型
        * + 1 // 空
        * + 8 // 消息 id long 类型
        * + 4 // 消息体body长度, int类型
        */
/************************************************************************************************************************/
public class MessageProtocol {
    /***协议头长度***/
    public static final int HEAD_LENGTH = 16;

    /***魔数***/
    public static final short MAGIC = (short)0xbabe;

    /***消息类型***/
    public static final byte REQUEST = 1;
    public static final byte RESPONSE = 2;
    public static final byte ACK = 126;
    public static final byte HEART_BEAT = 127;

    private byte sign;
    private byte status;
    private long id;
    private int bodyLongth;

    public byte getSign() {
        return sign;
    }

    public void setSign(byte sign) {
        this.sign = sign;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBodyLongth() {
        return bodyLongth;
    }

    public void setBodyLongth(int bodyLongth) {
        this.bodyLongth = bodyLongth;
    }
}
