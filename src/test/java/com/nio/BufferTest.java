package com.nio;

import org.junit.Test;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * channel->buffer  buffer->channel
 *
 * buffer是一个特定的原生类型数据的容器，底层其实就是一个数组
 *
 * buffer 是一种有序的，线性，特定原生类型元素的序列
 *
 * buffer 7个常用的原生类型子类： intBuffer,floatBuffer,charBuffer,doubleBuffer,shortBuffer,longBuffer,byteBuffer
 *
 * buffer 三个重要的属性 capacity,limit,position
 *
 * capacity: 包含的元素的数量，不可能是负数，数值永远不会变化,由allocate()决定
 *
 * limit：第一个不能再读或者写的元素索引，不可能为负数，永远不会超过capacity
 *
 * position: 下一个将要去读或者写的元素的索引
 *
 *
 * Created by wangyong on 2017/8/26.
 */
public class BufferTest {

    @Test
    public void bufferTest() {

        //allocate 指定capacity的值，一旦指定不可以更改
        IntBuffer intBuffer = IntBuffer.allocate(10);

        for (int i = 0; i < 4; i++) {
            int randomNum = new SecureRandom().nextInt(10);
            intBuffer.put(randomNum);
        }

        //调用fiip方法的时候limit为position的索引值,position 会置为0
        intBuffer.flip();

        //当前position<limit
        while (intBuffer.hasRemaining()) {

            //当前下标的值，position自增为1
            System.out.println(intBuffer.get());
        }

        intBuffer.flip();
        System.out.println("-----");

        for (int i = 0; i < 2; i++) {
            int randomNum = new SecureRandom().nextInt(10);
            intBuffer.put(randomNum);
        }

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

    }
}
