package my.rpc.core.client;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import my.rpc.core.common.RpcDecoder;
import my.rpc.core.common.RpcEncoder;
import my.rpc.core.common.RpcInvocation;
import my.rpc.core.common.RpcProtocol;
import my.rpc.core.common.config.ClientConfig;
import my.rpc.core.proxy.jdk.JDKInvocationHandler;
import my.rpc.core.proxy.jdk.JDKProxyFactory;
import my.rpc.interfaces.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static my.rpc.core.common.cache.CommonClientCache.SEND_QUEUE;

/**
 * @author mao
 * @ClassName Client.java
 * @Description TODO
 * @createTime 2023年06月20日 21:25:00
 */
public class Client {

    private static Logger logger = LoggerFactory.getLogger(Client.class);

    private ClientConfig clientConfig;

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public RpcReference startClientApplication() throws InterruptedException {
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new RpcEncoder());
                socketChannel.pipeline().addLast(new RpcDecoder());
                socketChannel.pipeline().addLast(new ClientHandler());
            }
        });
        ChannelFuture channelFuture = bootstrap.connect(clientConfig.getServerAddr(), clientConfig.getPort()).sync();
        logger.info("============客户端启动===============");
        startClient(channelFuture);
        RpcReference rpcReference = new RpcReference(new JDKProxyFactory());
        return rpcReference;
    }

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        client.setClientConfig(new ClientConfig("localhost", 9090));
        RpcReference rpcReference = client.startClientApplication();
        // 获取调用方法的代理对象
        DataService proxyDataService = rpcReference.getProxy(DataService.class);
        String[] sendData = new String[]{"mao", "yua", "opasd"};
        for (int i = 0; i < 100; i++) {
            Thread.sleep(300);
            int index = RandomUtil.randomInt(1, 4);
            System.out.println("index:" + index);
            String sendMsg = sendData[index % 3] + System.currentTimeMillis();
            String result = proxyDataService.sendData(sendMsg);
            System.out.println("result:" + result);
        }
        System.exit(1);
    }


    public void startClient(ChannelFuture channelFuture) {
        AsyncSendJob asyncSendJob = new AsyncSendJob(channelFuture);
        new Thread(asyncSendJob).start();

    }


    class AsyncSendJob implements Runnable {

        private ChannelFuture channelFuture;

        public AsyncSendJob(ChannelFuture channelFuture) {
            this.channelFuture = channelFuture;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // 阻塞模式
                    RpcInvocation data = SEND_QUEUE.take();
                    String json = JSON.toJSONString(data);
                    RpcProtocol rpcProtocol = new RpcProtocol(json.getBytes());
                    channelFuture.channel().writeAndFlush(rpcProtocol);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
