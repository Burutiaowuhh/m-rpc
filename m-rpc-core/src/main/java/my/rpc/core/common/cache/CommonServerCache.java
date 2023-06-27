package my.rpc.core.common.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname CommonServerCache
 * @Date 2023/6/27 14:29
 * @Created by mao
 * @Description
 */
public class CommonServerCache {

    public static final ConcurrentHashMap<String, Object> PROVIDER_CLASS_MAP = new ConcurrentHashMap<>();

}
