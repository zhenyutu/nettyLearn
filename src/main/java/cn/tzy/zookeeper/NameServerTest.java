package cn.tzy.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import javax.swing.text.TableView;

/**
 * Created by tuzhenyu on 17-11-29.
 * @author tuzhenyu
 */
public class NameServerTest {
    public static void main(String[] args) throws Exception{
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181",5000,null);
        Thread.sleep(1000);
//        zooKeeper.create("/id-","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        NameServerTest n = new NameServerTest();
        System.out.println(n.generateID(zooKeeper,"/id-"));
    }

    private String generateID(ZooKeeper zooKeeper,String root) throws Exception{
        final String seq = zooKeeper.create(root,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        Stat stat = new Stat();
        zooKeeper.getData(seq,null,stat);
        zooKeeper.delete(seq,stat.getVersion());
        return seq;
    }
}
