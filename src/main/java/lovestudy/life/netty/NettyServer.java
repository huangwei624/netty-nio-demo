package lovestudy.life.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.ArrayList;

/**
 * Create by huangwei on 2020/5/21 0021
 */
public class NettyServer {

    // 用来接收进来的连接
    private static EventLoopGroup bossGroup = new NioEventLoopGroup();
    // 用来处理已经被接收的连接，一旦bossGroup接收到连接，就会把连接信息注册到workerGroup上
    private static EventLoopGroup workerGroup = new NioEventLoopGroup();
    // netty 服务类
    private static ServerBootstrap bootstrap = new ServerBootstrap();

    public NettyServer() {
        // 配置netty 服务
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128) // tcp最大缓存链接个数
                .childOption(ChannelOption.SO_KEEPALIVE, true) //保持连接
                .handler(new LoggingHandler(LogLevel.INFO)) // 打印日志级别
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new RpcEncode());
                        pipeline.addLast(new RpcDecode(RpcResponse.class));
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new ServerHandler());
                    }
                });
    }

    /**
     * 开启服务监听端口
     */
    public void bind(int port) throws InterruptedException {
            // 启动服务，开启监听端口
            ChannelFuture cf = bootstrap.bind(port).sync();
            if(cf.isSuccess()) System.out.println("服务启动，监听端口，" + port);
            // 等待服务端口的关闭；在这个例子中不会发生，但你可以优雅实现；关闭你的服务, 这里会阻塞
            // cf.channel().closeFuture().sync();
    }


    public static void main(String[] args) throws InterruptedException {
        NettyServer nettyServer = new NettyServer();
        nettyServer.bind(8080);
        nettyServer.bind(8081);
    }
}
