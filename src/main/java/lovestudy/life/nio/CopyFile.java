package lovestudy.life.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用nio 技术实现文件的拷贝
 */
public class CopyFile {
    public static void main(String[] args) {
        String sourceFile = "E:\\hw\\demo\\netty-nio-demo\\src\\main\\java\\lovestudy\\life\\nio\\a.txt";
        String targetFile = "E:\\hw\\demo\\netty-nio-demo\\src\\main\\java\\lovestudy\\life\\nio\\b.txt";
        copyFile(sourceFile, targetFile);
    }

    private static void copyFile(String sourceFile, String targetFile){
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(sourceFile);
            FileChannel inChannel = fileInputStream.getChannel();
            fileOutputStream = new FileOutputStream(targetFile);
            FileChannel outChannel = fileOutputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (-1 != inChannel.read(byteBuffer)) {
                // 切换到读模式
                byteBuffer.flip();
//                long write = channel.write(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
                outChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
