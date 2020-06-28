# ThreadLocal

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
