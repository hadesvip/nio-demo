package com.nio;

import java.nio.channels.Selector;
import org.junit.Test;

/**
 * nio-->selector
 *
 * selector 一个多路复用的SelectableChannel对象， 一个selector执行selector类的open方法被创建，使用系统默认的selector
 * provider创建一个新的selector。 selector也可以使用自定义的selector provider的openSelector方法创建。selector保持打开直到调用它的close方法关闭。
 *
 * <p> 一个可选择的channel注册到一个selector 通过SelectionKey 对象表示，一个selector维护者3个 selection keys 的集合：</p>
 *
 * <p>1.key set 包含当前channel注册到selector的所有的key，通过调用keys方法返回这个集合。</p>
 *
 * <p>2.selected-key set是这样的key的集合：每一个key的 通道被检测到，准备好针对于至少一种操作由感兴趣的key
 * set标识的操作在之前的选择操作当中，通过selectedKeys方法返回，selected-key是key set的一个子集。</p>
 *
 * <p>3.cancelled-key set 是这样的key的集合，已经被删除但是channel还没取消注册。这个集合不是直接可以访问的。cancelled-key也是key
 * set的一个子集。</p>
 *
 * <p>所有的这个3个集合在新创建的selector都是空的。</p>
 *
 * 通过channel的register方法注册一个channel的副作用是一个key添加到一个选择器的key set中。通过选择操作，cancelled key将会从key set移除。key
 * set本身是不能直接修改的。
 *
 * 当一个key被取消，将会添加到selector的cancelled-key set中，通过channel的closing和执行它的cancel方法。取消一个key将会导致它的channel
 * 在下一次选择操作，取消注册。同时这个key将会从所有的选择器的key set中移除。
 *
 * 通过选择操作，key被添加到selected-key中。通过执行集合的remove方法或者从一个集合中获取iterator 执行remove方法，这个key会直接会从selected-key中移除。key永远不会通过其他方式从selected-key移除。它们并不是作为选择操作的副作用被移除的。
 * key不能直接添加到一个selected-key集合中。
 *
 * selection
 *
 * 通过每一个选择操作，key从一个selector的selected-key被添加，移除和从自身key(key set)，cancelled-key集合中移除。
 *
 * selection通过select(),select(long),select(now) 方法表现，包含3个步骤：
 *
 * 1. cancelled-key集合中的每一个key作为一个成员从每一个key set中移除，它的channel取消注册。这个步骤使得cancelled-key 集合为空。
 *
 * 2.底层操作系统会被查询进行一个更新，来去应对每个剩余的channel准备状况，以执行这个key关注的集合标识出来的操作作为selection 操作开始的时候。
 *
 * 3. 对于准备好至少有这样一个操作的channel，下面的两个操作将会执行一个：
 *
 * 1.
 *
 * Concurrency 并发
 *
 * @author wangyong
 */
public class SelectorTest {


  @Test
  public void selectorTest() throws Exception {

    int[] ports = new int[5];

    Selector selector = Selector.open();


  }

}
