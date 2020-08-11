# ThreadLocal

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

- WeakReference标志性的特点是：reference实例不会影响到被应用对象的GC回收行为（即只要对象被除WeakReference对象之外所有的对象解除引用后，该对象便可以被GC回收），只不过在被对象回收之后，reference实例想获得被应用的对象时程序会返回null
- WeakReference是Java语言规范中为了区别直接的对象引用（程序中通过构造函数声明出来的对象引用）而定义的另外一种引用关系。

- ThreadLocal 使用不当可能会造成内存泄漏。避免内存泄漏的方法是在方法调用结束前执行 ThreadLocal 的 remove 方法。
    - 如果线程一直存活，比如是线程池中的线程。经过 GC 后 Entry 持有的 ThreadLocal 引用断开，Entry 的 key 为空，value 不为空，那么该 Entry 的 value 持有的 Object 就不会被回收掉。这样就产生了内存泄漏。
    - 使用完执行 remove 方法就行了。
