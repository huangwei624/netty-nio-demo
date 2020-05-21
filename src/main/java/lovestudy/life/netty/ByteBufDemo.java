package lovestudy.life.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Create by huangwei on 2020/5/21 0021
 */
public class ByteBufDemo {

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(1024);
        buffer.writeBytes(new byte[]{1, 2, 3, 5, 4});
        byte b = buffer.readByte();
        System.out.println("读取一个字节：" + b);
        System.out.println(buffer.readableBytes());
    }
}
