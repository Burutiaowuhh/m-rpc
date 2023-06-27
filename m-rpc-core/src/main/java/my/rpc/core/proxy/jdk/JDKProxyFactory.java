package my.rpc.core.proxy.jdk;

import my.rpc.core.common.RpcInvocation;
import my.rpc.core.proxy.ProxyFactory;

import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static my.rpc.core.common.cache.CommonClientCache.RES_MAP;
import static my.rpc.core.common.cache.CommonClientCache.SEND_QUEUE;

/**
 * @Classname JDKProxyFactory
 * @Date 2023/6/27 17:41
 * @Created by mao
 * @Description
 */
public class JDKProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(final Class clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new JDKInvocationHandler(clazz));
    }
}
