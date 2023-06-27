package my.rpc.core.proxy.jdk;

import my.rpc.core.common.RpcInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static my.rpc.core.common.cache.CommonClientCache.RES_MAP;
import static my.rpc.core.common.cache.CommonClientCache.SEND_QUEUE;

/**
 * @Classname JDKInvocationHandler
 * @Date 2023/6/27 17:43
 * @Created by mao
 * @Description
 */
public class JDKInvocationHandler implements InvocationHandler {


    public static final Object OBJECT = new Object();

    private Class<?> clazz;

    public JDKInvocationHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setTargetServiceName(clazz.getName());
        rpcInvocation.setTargetMethod(method.getName());
        rpcInvocation.setArgs(args);
        rpcInvocation.setUuid(UUID.randomUUID().toString());
        RES_MAP.put(rpcInvocation.getUuid(), OBJECT);
        SEND_QUEUE.add(rpcInvocation);
        int count = 0;
        while (count < 3) {
            Object resp = RES_MAP.get(rpcInvocation.getUuid());
            if (resp instanceof RpcInvocation) {
                return ((RpcInvocation) resp).getResponse();
            }
            Thread.sleep(1000);
            count++;
        }

        throw new TimeoutException("client wait server's response timeout!");
    }
}
