package cn.tzy.zookeeper;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * Created by tuzhenyu on 17-11-28.
 * @author tuzhenyu
 */
public class ACLTest {
    public static void main(String[] args) throws Exception{
        final CountDownLatch downLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("receive the watch event");
                if (Event.KeeperState.SyncConnected==watchedEvent.getState())
                    downLatch.countDown();
            }
        });
        downLatch.await();
        System.out.println("zookeeper session established");

        zooKeeper.addAuthInfo("digest","foo:true".getBytes());
        zooKeeper.create("/zkTest","123".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);

        ZooKeeper zooKeeper1 = new ZooKeeper("127.0.0.1:2181",5000,null);
        System.out.println(zooKeeper1.getData("/zkTest",false,null));
    }
}
