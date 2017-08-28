package com.nio;


import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 常用的channel
 * FileChannel
 * DatagramChannel
 * SocketChannel
 * ServerSocketChannel
 * Created by wangyong on 2017/8/26.
 */
public class ChannelTest {

  @Test
  public void fileChannelTest() throws IOException {

    FileInputStream inputStream = new FileInputStream("input.txt");
    FileOutputStream outputStream = new FileOutputStream("out.txt");
    FileChannel inputChannel = inputStream.getChannel();
    FileChannel outChannel = outputStream.getChannel();

    ByteBuffer byteBuffer = ByteBuffer.allocate(512);
    ByteBuffer byteBuffer1 = ByteBuffer.allocate(512);
    int read = inputChannel.read(byteBuffer);
    System.out.println(read);

    byteBuffer.flip();

    outChannel.write(byteBuffer);

    inputChannel.position(0);
    int read2 = inputChannel.read(byteBuffer1);
    System.out.println(read2);
    byteBuffer1.flip();
    outChannel.write(byteBuffer1);

//        while (true) {
//
//            //channel->byteBuffer
//            int read = inputChannel.read(byteBuffer);
//
//            if (read == -1) {
//                break;
//            }
//
//            //limit=position,position=0
//            byteBuffer.flip();
//
//            //position=limit
//            outChannel.write(byteBuffer);
//
//        }

    outChannel.close();
    outputStream.close();
    inputChannel.close();
    inputStream.close();

  }

}
