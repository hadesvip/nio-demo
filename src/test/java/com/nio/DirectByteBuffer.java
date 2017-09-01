package com.nio;

import java.nio.ByteBuffer;
import org.junit.Test;

/**
 * 直接缓冲:堆外内存 ByteBuffer.allocateDirect源码内部是new DirectByteBuffer(capacity)， 构造方法里面有这段代码
 * //使用的sun包的方法，allocateMemory是用的native方法，使用的堆外内存(即操作系统内存) base = unsafe.allocateMemory(size);
 *
 * //释放内存 Deallocator  unsafe.freeMemory(address);
 *
 * Byte类中有个成员变量 address ，只用于直接缓冲，用于jni GetDirectBufferAddress中挂起以提高速度
 *
 * ByteBuffer.allocate（HeapByteBuffer） 这个操作会多一个拷贝的过程，将jvm中存储的数据拷贝到操作系统中，然后再跟IO交互
 *
 * 为什么操作系统不直接操作jvm中的数据？ 因为jvm有一个垃圾回收，先标记后压缩（标记移动），如果操作系统正在操作buffer，发生了垃圾回收，数据就乱了
 *
 * ByteBuffer.allocateDirect（DirectByteBuffer） 少了数据拷贝的过程（零拷贝），直接跟IO交互
 *
 *
 *
 *
 * Created by wangyong on 2017/8/29.
 */
public class DirectByteBuffer {


  @Test
  public void directByteBufferTest() {
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
  }

}
