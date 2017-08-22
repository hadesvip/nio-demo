package com.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;
import org.junit.Test;

/**
 * NIO 测试
 *
 * java.io
 * 核心概念流(stream),面向流编程。java中，一个流要么是输出流要么是输入流，不可能既是输入流又是输出流。
 *
 * java.nio
 * selector,channel,buffer,在java.nio，是面向块(block)或者缓冲区(buffer)编程。
 * buffer 本身就是一块内存，底层实现上，就是一个数组。数据的读和写都是通过buffer实现的。
 * channel 指的是可以向其写入数据或者从中读取数据的对象，他类似于java.io的stream。
 * 所有数据读写都是通过buffer，永远不会出现直接向channel写入数据和直接从channel中读取数据的情况。
 * 与stream不同的是，channel是双向的，一个流只可能是inputstream或者是outputstream，channel打开后则可以进行读取，写入或者读写。
 * 由于channel是双向的，因此它能更好的反映出底层操作系统的真实情况，在linux中，底层操作系统的通道就是双向的。
 *
 * Created by wangyong on 2017/8/22.
 */
public class NIOTest {


  /**
   * 缓冲区
   */
  @Test
  public void bufferTest() {
    IntBuffer buffer = IntBuffer.allocate(10);
    for (int i = 0; i < buffer.capacity(); i++) {
      int randomNum = new SecureRandom().nextInt(20);
      buffer.put(randomNum);
    }

    //反转
    buffer.flip();

    while (buffer.hasRemaining()) {
      System.out.println(buffer.get());
    }
  }

  @Test
  public void fileChannelInputTest() throws Exception {
    FileInputStream fileInputStream = new FileInputStream("demo.txt");
    FileChannel fileChannel = fileInputStream.getChannel();
    ByteBuffer byteBuffer = ByteBuffer.allocate(512);

    //文件内容写入buffer中
    fileChannel.read(byteBuffer);

    //反转
    byteBuffer.flip();

    while (byteBuffer.remaining() > 0) {
      byte b = byteBuffer.get();
      System.out.println((char) b);
    }
    fileInputStream.close();
  }


  @Test
  public void fileChannelOutTest() throws Exception {
    FileOutputStream fileOutputStream = new FileOutputStream("nio.txt");
    FileChannel fileChannel = fileOutputStream.getChannel();

    ByteBuffer byteBuffer = ByteBuffer.allocate(512);
    byte[] bytes = "hello world,welcome,nio...".getBytes();

    for (int i = 0; i < bytes.length; i++) {
      byteBuffer.put(bytes[i]);
    }
    byteBuffer.flip();
    fileChannel.write(byteBuffer);
    fileOutputStream.close();
  }


}
