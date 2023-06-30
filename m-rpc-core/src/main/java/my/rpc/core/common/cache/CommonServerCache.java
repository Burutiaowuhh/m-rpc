package my.rpc.core.common.cache;

import my.rpc.core.registry.URL;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname CommonServerCache
 * @Date 2023/6/27 14:29
 * @Created by mao
 * @Description
 */
public class CommonServerCache {

    public static final ConcurrentHashMap<String, Object> PROVIDER_CLASS_MAP = new ConcurrentHashMap<>();

    public static final Set<URL> PROVIDER_URL_SET = new HashSet<>();

}
