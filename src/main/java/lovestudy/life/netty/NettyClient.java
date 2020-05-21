package lovestudy.life.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;


/**
 * Create by huangwei on 2020/5/18 0018
 */
public class NettyClient {
    private final String host;
    private final int port;
    private Channel channel;

    // 连接服务端的端口号地址和端口号
    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        final EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)  // 使用NioSocketChannel来作为连接用的channel类
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("正在连接中...");
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new RpcEncode());
                        pipeline.addLast(new RpcDecode(RpcResponse.class));
                        pipeline.addLast(new ClientHandler()); //客户端处理类

                    }
                });
        //发起异步连接请求，绑定连接端口和host信息
        final ChannelFuture future = b.connect(host, port).sync();

        future.addListener((ChannelFutureListener) arg0 -> {
            if (future.isSuccess()) {
                System.out.println("连接服务器成功");
            } else {
                System.out.println("连接服务器失败");
                future.cause().printStackTrace();
                group.shutdownGracefully(); //关闭线程组
            }
        });

        this.channel = future.channel();
    }

    /**
     * 客服端连接服务器后，会返回一个通道，通过这个通道可以实现向服务器端发送数据
     * 客服端和服务端的交互主要通过这个通道 channel 进行交互
     * @return
     */
    public Channel getChannel() {
        return channel;
    }


    public static void main(String[] args) throws Exception {
        NettyClient nettyClient = new NettyClient("127.0.0.1", 8080);
        nettyClient.start();
        Channel channel = nettyClient.getChannel();
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setCode(0);
        rpcResponse.setMsg("发送消息成功");
        rpcResponse.setData(new Date());
        channel.writeAndFlush(rpcResponse);
    }

}
