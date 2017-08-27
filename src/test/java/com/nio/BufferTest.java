package com.nio;

import org.junit.Test;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * channel->buffer  buffer->channel
 * <p>
 * buffer是一个特定的原生类型数据的容器，底层其实就是一个数组
 * <p>
 * buffer 是一种有序的，线性，特定原生类型元素的序列
 * <p>
 * buffer 7个常用的原生类型子类： intBuffer,floatBuffer,charBuffer,doubleBuffer,shortBuffer,longBuffer,byteBuffer
 * <p>
 * buffer 三个重要的属性 capacity,limit,position；还有1个不常用的属性:mark； 他们之间的关系是0<=mark<=position<=limit<=capacity
 * <p>
 * capacity: 包含的元素的数量，不可能是负数，数值永远不会变化,由allocate()决定
 * <p>
 * limit：第一个不能再读或者写的元素索引，不可能为负数，永远不会超过capacity
 * <p>
 * position: 下一个将要去读或者写的元素的索引,不可能为负数，永远不会超过limit
 * <p>
 * Transferring data
 * buffer的子类都有两类的get和put操作
 * 相对操作
 * 从当前位置开始读或者写一个或者多个元素，然后根据转移元素的数量增加position,
 * 如果请求转移超过了limit，相对get操作将会抛出BufferUnderflowException,
 * 相对put操作抛出BufferOverflowException,这两种情况下，没有数据被传输。
 * <p>
 * 绝对操作
 * 绝对显示操作元素的索引，不会影响position.如果索引参数超过limit,绝对get和put的操作抛出IndexOutOfBoundsException。
 * <p>
 * 数据也可以通过适当的channel的io 操作传输到缓冲区。通常取决于当前的position.
 * <p>
 * marking和resetting
 * <p>
 * buffer的mark是一个索引，执行reset方法，position将会重置为mark.mark通常不定义，但是一旦他被定义，他永远不会为负数，并且不会大于position
 * 如果mark被定义，当position或者limit 小于mark的值，mark将会被丢弃（mark=-1）
 * 如果mark没有被定义，执行reset方法就会抛出 InvalidMarkException 异常。
 * <p>
 * Invariants
 * mark,position,limit capacity 关系
 * 0 <= mark <= position <= limit <= capacity
 * 一个新创建buffer通常有一个为0 的position和一个未定义的mark.初始化的limit可能为0或者其他值，这个取决于buffer的类型和构建的方式。
 * 新分配的buffer的每个元素初始为0
 * <p>
 * Clearing, flipping, and rewinding
 * 除了position,limit,capacity值和mark和reset方法，buffer还定义了下面操作
 * clear
 * 为新的序列通道读或者相对put操作准备一个buffer：将limit设置为capacity,position为0
 * flip
 * 为新的序列通道写或者相对的get操作准备一个buffer：limit=当前的position值，position=0
 * rewind
 * 已存在数据重复读准备一个buffer：limit值不变，position=0
 * <p>
 * Read-only buffers
 * 每一个buffer都是可读的，但是不是每一个buffer都是可写的，
 * 当只读buffer调用改变值的方法时，将会抛出 ReadOnlyBufferException
 * 只读buffer内容没法改变，但是limit,mark,position的值可以改变。
 * 缓冲区是否可读可以调用isreadonly方法判断。
 * <p>
 * Thread safety
 * 多个线程并发，buffer不是线程安全。当一个缓冲区被多个线程访问时，需要加入恰当的同步限制访问
 * <p>
 * buffer方法具备调用链的方式
 * <p>
 * Created by wangyong on 2017/8/26.
 */
public class BufferTest {

    @Test
    public void bufferTest() {

        //allocate 指定capacity的值，一旦指定不可以更改
        //执行allocate方法,position为0,limit设置为capacity的值。
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


    @Test
    public void postionTest() {
        IntBuffer intBuffer = IntBuffer.allocate(10);
        for (int i = 0; i < 3; i++) {
            int randomNum = new SecureRandom().nextInt(10);
            intBuffer.put(randomNum);
        }
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

        intBuffer.limit(intBuffer.capacity());
        int randomNum = new SecureRandom().nextInt(10);
        System.out.println("生成的random：" + randomNum);
        //标记
        intBuffer.mark();
        intBuffer.put(randomNum);
        intBuffer.reset();


        System.out.println(intBuffer.get());


    }
}
