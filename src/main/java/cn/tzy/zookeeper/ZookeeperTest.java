package cn.tzy.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * Created by tuzhenyu on 17-11-26.
 * @author tuzhenyu
 */
public class ZookeeperTest {
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
    }
}
