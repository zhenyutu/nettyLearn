package cn.tzy.chapter11.webSocketChat.entry;

import com.alibaba.fastjson.JSON;

/**
 * Created by tuzhenyu on 17-10-31.
 * @author tuzhenyu
 */
public class Request {
    private String clientId;
    private int serviceId;
    private String name;
    private String message;

    public static Request create(String str){
        return JSON.parseObject(str,Request.class);
    }

    public String getRequestId() {
        return clientId;
    }

    public void setRequestId(String clientId) {
        this.clientId = clientId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
