# ThreadLocal

- 由于 ThreadLocalMap 的 Entry 没有注册 ReferenceQueue，所以垃圾回收线程无法通知 ThreadLocalMap 有哪些 entry 的 key 被回收了。ThreadLocalMap 中的部分操作会清理“不新鲜的 entry”，但是这种清理不是有目的性的，而是碰到了才清理，也只清理碰到的“不新鲜的 entry”。不像 WeakHashMap 那样，能根据 queue 一次性的清理掉所有“不新鲜的 entry”。所以在使用 ThreadLocalMap 时，是存在着内存泄露的风险的，而解决办法就是及时的调用其 remove() 方法。
- 由于 ThreadLocalMap 的 Entry 没有注册 ReferenceQueue，所以 Entry 直接从 Active 状态迁移到了 Inactive 状态，其 key 也就被垃圾回收器回收了，所以就为 null 了。
  


- 资源的线程私有化 解决竞争资源问题

- ThreadLocalMap 则使用了「开放寻址法」中的「线性探测」。即，当某个位置出现冲突时，从当前位置往后查找，直到找到一个空闲位置。

- 每个线程thread对象里有一个map(ThreadLocalMap)，map的键值对(Entry)是 ThreadLocal - value, 每个 ThreadLocal 对象都是线程对象中map的一个key

- 当 ThreadLocal 实例没有其他地方引用时，使用了WeakReference下一次gc发生会被回收 
```

        static class Entry extends WeakReference<ThreadLocal<?>> {
            /** The value associated with this ThreadLocal. */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }
``` 

- WeakReference标志性的特点是：reference实例不会影响到被引用对象的GC回收行为（即只要对象被除WeakReference对象之外所有的对象解除引用后，该对象便可以被GC回收），只不过在被对象回收之后，reference实例想获得被引用的对象时程序会返回null
- WeakReference是Java语言规范中为了区别直接的对象引用（程序中通过构造函数声明出来的对象引用）而定义的另外一种引用关系。

- ThreadLocal 使用不当可能会造成内存泄漏。避免内存泄漏的方法是在方法调用结束前执行 ThreadLocal 的 remove 方法。
    - 如果线程一直存活，比如是线程池中的线程。经过 GC 后 Entry 持有的 ThreadLocal 引用断开，Entry 的 key 为空，value 不为空，那么该 Entry 的 value 持有的 Object 就不会被回收掉。这样就产生了内存泄漏。
    - 使用完执行 remove 方法就行了。

- static修饰
    - 如果不用static修饰 每创建一个该类的实例就会相应创建一个该ThreadLocal对象 导致错误、浪费资源；
    - 如果用static修饰 也同时会导致该ThreadLocal对象被强引用 不会被gc

- value 为什么不设计成 弱引用
    - 不设置为弱引用，是因为不清楚这个Value除了map的引用还是否还存在其他引用，如果不存在其他引用，当GC的时候就会直接将这个Value回收，而此时我们的ThreadLocal还处于使用期间，就会造成Value为null的错误，所以将其设置为强引用