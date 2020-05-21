package lovestudy.life;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Create by huangwei on 2020/5/21 0021
 */
public class A {

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);
        buffer.writeCharSequence("abcdefghij", Charset.forName("utf8"));
        System.out.println(buffer.readableBytes());
        buffer.markReaderIndex();
        buffer.readByte();
        buffer.resetReaderIndex();
        System.out.println(buffer.readableBytes());

    }

}
