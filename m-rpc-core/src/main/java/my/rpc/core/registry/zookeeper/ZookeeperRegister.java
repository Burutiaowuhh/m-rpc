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

    public String getConsumerUrlPath(URL url) {
        return ROOT + "/" + url.getServiceName() + "/consumer/" + url.getApplicationName() + ":" + url.getParameters().get("host") + ":";
    }

    @Override
    public void register(URL url) {
        if (!zkClient.existNode(ROOT)) {
            zkClient.createPersistentData(ROOT, "");
        }
        String data = URL.buildProviderUrlStr(url);
        String providerPath = getProviderPath(url);
        if (!zkClient.existNode(providerPath)) {
            zkClient.createTemporaryData(providerPath, data);
        } else {
            zkClient.deleteNode(providerPath);
            zkClient.createTemporaryData(providerPath, data);
        }
        super.register(url);
    }

    @Override
    public void unRegister(URL url) {
        zkClient.deleteNode(getProviderPath(url));
        super.unRegister(url);
    }


    @Override
    public void subscribe(URL url) {
        if (!zkClient.existNode(ROOT)) {
            zkClient.createPersistentData(ROOT, "");
        }
        String data = URL.buildConsumerUrlStr(url);
        String consumerUrlPath = getConsumerUrlPath(url);
        if (!zkClient.existNode(consumerUrlPath)) {
            zkClient.createTemporaryData(consumerUrlPath, data);
        } else {
            zkClient.deleteNode(consumerUrlPath);
            zkClient.createTemporaryData(consumerUrlPath, data);
        }
        super.subscribe(url);
    }


    @Override
    public void doUnSubscribe(URL url) {
        zkClient.deleteNode(getConsumerUrlPath(url));
        super.doUnSubscribe(url);
    }

    @Override
    public void doAfterSubscribe(URL url) {
        // 监听是否有新服务注册

    }


}
