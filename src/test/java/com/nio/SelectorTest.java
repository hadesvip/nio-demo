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
 * 2.底层操作系统会被查询进行一个更新，来去应对每个剩余的channel准备状况，以执行这个key关注的集合标识出来的操作作为selection 操作开始的时候。对于准备好至少有这样一个操作的channel，下面的两个操作将会执行一个：
 *
 *    1. 如果channel的key还不在selected-key的集合中，那么将会添加到这个set(selected-set)中，它的准备的操作集合被修改以确定通道现在已被报告准备的那些操作,
 *    在准备集合中记录的任何准备信息都会丢弃掉。
 *
 *    2. 否则，channel的key已经存在selected-key集合中,因此它的准备操作集合被修改以识别任何新的操作，该通道已在通道中准备就绪。在准备集合中任何准备信息都会被记录下来。
 *        换句话说，底层系统返回的准备集是位与键当前的准备集之间的位连接
 *
 *    如果这个步骤开始的key set的所有key都有空的关注集合，那么无论selected-key还是任何key的准备操作集合将不会更新。
 *
 * 3. 如果步骤2中键被添加到cancelled-key 集合中，就会被作为步骤1处理。
 *
 * 不管一个selection的操作堵塞等待着一个或者多个通道变成准备状态，如果是这样，等待多长时间，这是三个selection方法的本质区别。
 *
 * Concurrency 并发性
 * selector自身是安全的，但是它的set并不是安全的。
 *  一个selector的key和selector的集合在多个并发线程使用时，通常是不安全的。如果一个线程直接修改一个或者多个集合，访问应该通过set的本身进行同步控制。
 *  通过set的iterator方法返回快速失败：如果set在iterator创建时候被修改，除了执行iterator自己的remove方法其他任何方式，一个 ConcurrentModificationException 异常将会抛出。
 *
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
