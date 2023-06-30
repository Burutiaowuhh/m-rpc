package my.rpc;

import my.rpc.core.registry.URL;
import my.rpc.core.registry.zookeeper.ZookeeperRegister;
import org.junit.Test;

/**
 * @Classname ZookeeperTest
 * @Date 2023/6/30 17:54
 * @Created by mao
 * @Description
 */
public class ZookeeperTest {


    @Test
    public void testZookeeperRegister() throws InterruptedException {
        ZookeeperRegister zookeeperRegister = new ZookeeperRegister("localhost:2181");
        URL url = new URL();
        url.setServiceName("my.rpc.core.Server.DataService");
        url.setApplicationName("mrpc-provider");
        url.addParameter("host", "198.18.0.1");
        url.addParameter("port", "9096");
        zookeeperRegister.register(url);

        Thread.sleep(600000);
    }

    @Test
    public void deleteNode() throws InterruptedException {
        ZookeeperRegister zookeeperRegister = new ZookeeperRegister("localhost:2181");
        URL url = new URL();
        url.setServiceName("my.rpc.core.Server.DataService");
        url.setApplicationName("mrpc-provider");
        url.addParameter("host", "198.18.0.1");
        url.addParameter("port", "9096");
        zookeeperRegister.unRegister(url);


        Thread.sleep(60000);
    }

}
