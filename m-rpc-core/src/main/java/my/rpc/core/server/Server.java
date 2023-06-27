package my.rpc.core.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import my.rpc.core.common.RpcDecoder;
import my.rpc.core.common.RpcEncoder;
import my.rpc.core.common.config.ServerConfig;

import static my.rpc.core.common.cache.CommonServerCache.PROVIDER_CLASS_MAP;

/**
 * @author mao
 * @ClassName Server.java
 * @Description TODO
 * @createTime 2023年06月20日 22:55:00
 */
public class Server {

    private static EventLoopGroup baseGroup = null;

    private static EventLoopGroup workerGroup = null;

    private ServerConfig serverConfig;

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void startApplication() throws InterruptedException {
        baseGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(baseGroup, workerGroup);
        // 此处不能写成 NioSCTPServerChannel.class
        // 否则 windows 中会提示SCTP not supported on this platform 异常
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.option(ChannelOption.SO_SNDBUF, 16 * 1024)
                .option(ChannelOption.SO_RCVBUF, 16 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                System.out.println("初始化连接通道信息，编解码处理器：定长处理器");
                socketChannel.pipeline().addLast(new RpcEncoder());
                //                ch.pipeline().addLast(new FixedLengthFrameDecoder(3));
                //指定通过回车换行符来识别每次发送的数据，但是一旦当文本数据超过类maxLength就会抛出异常
//                ch.pipeline().addLast(new LineBasedFrameDecoder(5));
                //指定特殊符号的分割处理
//                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(50, Unpooled.copiedBuffer("[end]".getBytes())));
                socketChannel.pipeline().addLast(new RpcDecoder());
                socketChannel.pipeline().addLast(new ServerHandler());
            }
        });
        bootstrap.bind(serverConfig.getPort()).sync();
        System.out.println("server is open");
    }

    public void register(Object serviceBean) {
        Class<?>[] interfaces = serviceBean.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RuntimeException("service must have interfaces!");
        }
        if (interfaces.length > 1) {
            throw new RuntimeException("service must only have mon interfaces!");
        }
        Class<?> interfaceClass = interfaces[0];
        PROVIDER_CLASS_MAP.put(interfaceClass.getName(), serviceBean);
    }

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        server.setServerConfig(new ServerConfig(9090));
        server.register(new DataServiceImpl());
        server.startApplication();
    }

}
