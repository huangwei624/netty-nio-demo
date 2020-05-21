package lovestudy.life.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Create by huangwei on 2020/5/21 0021
 */
public class RpcDecode extends ByteToMessageDecoder {

    private Class<?> target;

    public RpcDecode(Class<?> target) {
        this.target = target;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
            throws Exception {
        // 空对象
        if(byteBuf.readableBytes() < 4){
            return;
        }
        int objLength = byteBuf.readInt();
        if(byteBuf.readableBytes() != objLength){
            // 数据包不完整，可能出现丢失数据，返回
            return ;
        }
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        list.add(JSONObject.parseObject(bytes, target));
    }
}
