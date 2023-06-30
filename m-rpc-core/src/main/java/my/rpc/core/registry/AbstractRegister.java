package my.rpc.core.registry;

import static my.rpc.core.common.cache.CommonClientCache.SUBSCRIBE_SERVICE_LIST;
import static my.rpc.core.common.cache.CommonServerCache.PROVIDER_URL_SET;

/**
 * @Classname AbstractRegister
 * @Date 2023/6/30 11:30
 * @Created by mao
 * @Description
 */
public abstract class AbstractRegister implements IRegistryService {

    @Override
    public void register(URL url) {
        PROVIDER_URL_SET.add(url);
    }

    @Override
    public void unRegister(URL url) {
        PROVIDER_URL_SET.remove(url);
    }

    @Override
    public void subscribe(URL url) {
        SUBSCRIBE_SERVICE_LIST.add(url.getServiceName());
    }

    @Override
    public void doUnSubscribe(URL url) {
        SUBSCRIBE_SERVICE_LIST.remove(url.getServiceName());
    }

    public abstract void doAfterSubscribe(URL url);


}
