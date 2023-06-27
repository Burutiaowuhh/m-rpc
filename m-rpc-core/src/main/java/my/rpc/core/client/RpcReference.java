package my.rpc.core.client;

import my.rpc.core.proxy.ProxyFactory;

/**
 * @Classname RpcReference
 * @Date 2023/6/27 19:53
 * @Created by mao
 * @Description
 */
public class RpcReference {

    private ProxyFactory proxyFactory;

    public RpcReference(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    public <T> T getProxy(Class<T> tClass) {
        return proxyFactory.getProxy(tClass);
    }

}
