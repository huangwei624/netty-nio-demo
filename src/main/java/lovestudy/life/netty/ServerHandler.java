package lovestudy.life.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Date;

/**
 * Create by huangwei on 2020/5/21 0021
 */
public class ServerHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse msg) throws Exception {
        System.out.println(msg);
        System.out.println("收到客服端的消息");
        Channel channel = channelHandlerContext.channel();
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setCode(1);
        rpcResponse.setMsg("你发送的消息我已经收到了");
        rpcResponse.setData(new Date());
        channel.writeAndFlush(rpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.out.println("数据读取异常");
    }
}
