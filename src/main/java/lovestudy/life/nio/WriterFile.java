package lovestudy.life.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class WriterFile {
    public static void main(String[] args) {
        String sourceFile = "E:\\hw\\demo\\netty-nio-demo\\src\\main\\java\\lovestudy\\life\\nio\\a.txt";
        String targetFile = "E:\\hw\\demo\\netty-nio-demo\\src\\main\\java\\lovestudy\\life\\nio\\b.txt";
        writeFile(sourceFile, targetFile);
    }

    private static void writeFile(String sourceFile, String targetFile){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(targetFile);
            FileChannel outChannel = fileOutputStream.getChannel();
            ByteBuffer byteBuffer = Charset.forName("utf-8").
                    encode("aaaaaaaaaaaaaaaaaaaa" +
                    "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                    "bcccccccccccccccccccccccccccccccc" +
                    "ddddddddddddddddddddddddddddddddd");
            outChannel.write(byteBuffer);
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
        }

    }
}
