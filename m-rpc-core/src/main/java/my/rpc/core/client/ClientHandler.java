package my.rpc.core.client;

import com.alibaba.fastjson2.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import my.rpc.core.common.RpcInvocation;
import my.rpc.core.common.RpcProtocol;

import static my.rpc.core.common.cache.CommonClientCache.RES_MAP;

/**
 * @author mao
 * @ClassName ClientHandler.java
 * @Description TODO
 * @createTime 2023年06月20日 21:25:00
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcProtocol rpcProtocol = (RpcProtocol) msg;

        byte[] rpcContent = rpcProtocol.getContent();
        String json = new String(rpcContent, 0, rpcContent.length);
        RpcInvocation rpcInvocation = JSON.parseObject(json, RpcInvocation.class);
        if (!RES_MAP.containsKey(rpcInvocation.getUuid())) {
            throw new IllegalArgumentException("server response is error");
        }
        RES_MAP.put(rpcInvocation.getUuid(), rpcInvocation);
        System.out.println("服务端相应数据：" + JSON.toJSONString(rpcInvocation.getResponse()));
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }
}
