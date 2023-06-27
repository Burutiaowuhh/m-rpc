package my.rpc.core.common.config;

/**
 * @Classname ServerConfig
 * @Date 2023/6/27 14:25
 * @Created by mao
 * @Description
 */
public class ServerConfig {

    private Integer port;

    public ServerConfig() {
    }

    public ServerConfig(Integer port) {
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
