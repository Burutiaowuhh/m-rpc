package my.rpc.core.common.config;

/**
 * @Classname ClientConfig
 * @Date 2023/6/27 15:14
 * @Created by mao
 * @Description
 */
public class ClientConfig {

    private String serverAddr;

    private Integer port;

    public ClientConfig() {
    }

    public ClientConfig(String serverAddr, Integer port) {
        this.serverAddr = serverAddr;
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }
}
