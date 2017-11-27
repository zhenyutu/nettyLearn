package cn.tzy.zookeeper;


import org.I0Itec.zkclient.ZkClient;

/**
 * Created by tuzhenyu on 17-11-26.
 * @author tuzhenyu
 */
public class ZkClientTest {
    public static void main(String[] args) {
        ZkClient client = new ZkClient("127.0.0.1:2181",5000);
//        client.createPersistent("/zkClient");
//        System.out.println(client.readData("/zkTest"));
//        client.delete("/zkTest");
        client.createPersistent("/zkTest",123);
        System.out.println(client.readData("/zkTest"));
    }
}
