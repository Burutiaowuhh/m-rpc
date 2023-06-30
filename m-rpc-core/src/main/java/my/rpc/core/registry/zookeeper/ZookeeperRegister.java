package my.rpc.core.registry.zookeeper;

import my.rpc.core.registry.AbstractRegister;
import my.rpc.core.registry.URL;

/**
 * @Classname ZookeeperRegister
 * @Date 2023/6/30 15:48
 * @Created by mao
 * @Description
 */
public class ZookeeperRegister extends AbstractRegister {

    private AbstractZookeeperClient zkClient;

    private String ROOT = "/mrpc";

    public ZookeeperRegister(String address) {
        this.zkClient = new CuratorZookeeperClient(address);
    }

    public String getProviderPath(URL url) {
        return ROOT + "/" + url.getServiceName() + "/provider/" + url.getParameters().get("host") + ":" + url.getParameters().get("port");
    }

    @Override
    public void register(URL url) {
        if (!zkClient.existNode(ROOT)) {
            zkClient.createPersistentData(ROOT, "");
        }
        String urlStr = URL.buildProviderUrlStr(url);
        String providerPath = getProviderPath(url);
        if (!zkClient.existNode(providerPath)) {
            zkClient.createTemporaryData(providerPath, urlStr);
        } else {
            zkClient.deleteNode(providerPath);
            zkClient.createTemporaryData(providerPath, urlStr);
        }
        super.register(url);
    }

    @Override
    public void unRegister(URL url) {
        zkClient.deleteNode(getProviderPath(url));
        super.unRegister(url);
    }

    @Override
    public void doAfterSubscribe(URL url) {

    }


}
