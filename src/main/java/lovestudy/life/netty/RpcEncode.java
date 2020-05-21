package lovestudy.life.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.json.JsonObjectDecoder;

/**
 * Create by huangwei on 2020/5/21 0021
 */
public class RpcEncode extends MessageToByteEncoder<RpcResponse> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse, ByteBuf byteBuf)
            throws Exception {
        // 将java 对象转换成字节数组
        byte[] bytes = JSON.toJSONBytes(rpcResponse);
        // 先写如对象所占字节的长度，方便解码是对数据校验
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
