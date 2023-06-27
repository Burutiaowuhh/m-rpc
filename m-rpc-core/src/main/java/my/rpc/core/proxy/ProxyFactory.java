package my.rpc.core.proxy;


/**
 * @Classname ProxyFactory
 * @Date 2023/6/27 17:39
 * @Created by mao
 * @Description
 */
public interface ProxyFactory {

    <T> T getProxy(Class clazz);

}
