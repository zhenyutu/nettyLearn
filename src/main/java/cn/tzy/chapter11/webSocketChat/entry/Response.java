package cn.tzy.chapter11.webSocketChat.entry;

/**
 * Created by tuzhenyu on 17-10-31.
 * @author tuzhenyu
 */
public class Response {
    private String requestId;
    private int serviceId;
    private boolean isSucc;
    private String message;


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public boolean getIsSucc() {
        return isSucc;
    }

    public void setIsSucc(boolean isSucc) {
        this.isSucc = isSucc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
