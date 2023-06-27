package my.rpc.core.common.cache;

import my.rpc.core.common.RpcInvocation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname CommonClientCache
 * @Date 2023/6/27 17:47
 * @Created by mao
 * @Description
 */
public class CommonClientCache {

    public static final ConcurrentHashMap<String, Object> RES_MAP = new ConcurrentHashMap<>();

    public static final BlockingQueue<RpcInvocation> SEND_QUEUE = new ArrayBlockingQueue<>(100);

}
