package my.rpc.core.registry.zookeeper;

import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * @Classname AbstractZookeeperClient
 * @Date 2023/6/30 15:21
 * @Created by mao
 * @Description
 */
public abstract class AbstractZookeeperClient {

    private String zkAddress;
    private int baseSleepTimes;
    private int maxRetryTimes;


    public AbstractZookeeperClient(String zkAddress) {
        this.zkAddress = zkAddress;
        this.baseSleepTimes = 1000;
        this.maxRetryTimes = 3;
    }

    public AbstractZookeeperClient(String zkAddress, Integer baseSleepTimes, Integer maxRetryTimes) {
        this.zkAddress = zkAddress;
        if (baseSleepTimes == null)  {
            this.baseSleepTimes = 1000;
        } else {
            this.baseSleepTimes = baseSleepTimes;
        }
        if (maxRetryTimes == null) {
            this.maxRetryTimes = 3;
        } else {
            this.maxRetryTimes = maxRetryTimes;
        }
    }

    public int getBaseSleepTimes() {
        return baseSleepTimes;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }


    /**
     * 获取指定目录下的子节点数据
     *
     * @param path
     * @return
     */
    public abstract List<String> getChildrenData(String path);



    /**
     * 创建持久化类型节点数据信息
     *
     * @param address
     * @param data
     */
    public abstract void createPersistentData(String address, String data);


    /**
     * 创建临时节点数据类型信息
     *
     * @param address
     * @param data
     */
    public abstract void createTemporaryData(String address, String data);



    /**
     * 判断是否存在节点
     *
     * @param address
     * @return
     */
    public abstract boolean existNode(String address);


    /**
     * 删除节点下边的数据
     *
     * @param address
     * @return
     */
    public abstract boolean deleteNode(String address);


    /**
     * 监听path路径下某个节点的数据变化
     *
     * @param path
     * @param watcher
     */
    public abstract void watchNodeData(String path, Watcher watcher);


    /**
     * 监听子节点下的数据变化
     *
     * @param path
     * @param watcher
     */
    public abstract void watchChildNodeData(String path, Watcher watcher);

}
