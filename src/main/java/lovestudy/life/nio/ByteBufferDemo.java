package lovestudy.life.nio;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ByteBufferDemo {
	public static void main(String[] args){
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put("abcdefg".getBytes());
		System.out.println("第二个元素："+(char)buffer.get(1));;  // b
		
		int position = buffer.position();
		int limit = buffer.limit();
		System.out.println("position: "+position+", limit: "+limit);  // 7   1024
		// 这里将limit 先设置为当前position 的位置，让后在将position 设置为0，一般为 读操作 做准备
		buffer.flip();      // 切换到读模式
		ByteBuffer slice = buffer.slice();
		int length = slice.array().length;
		System.out.println(length);
		System.out.println("position: "+buffer.position()+", limit: "+buffer.limit());  // 0     7
		System.out.println("是否有元素：" + buffer.hasRemaining());
		// 这个并不是删除所有元素的意思，而是将limit 重新设置为数组容量的大小, position 设置为0，为写操作做准备
		buffer.clear();
		System.out.println("是否有元素：" + buffer.hasRemaining());
		System.out.println("position: "+buffer.position()+", limit: "+	buffer.limit());    //  0    1024
	}
}
