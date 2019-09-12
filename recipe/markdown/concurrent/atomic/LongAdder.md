# LongAdder

- One or more variables that together maintain an initially zero long sum.一个或多个变量一起维持一个最初的零的long总和
- When updates (method add(long)) are contended across threads, the set of variables may grow dynamically to reduce contention. Method sum() (or, equivalently, longValue()) returns the current total combined across the variables maintaining the sum.
- 当更新(方法add(long))在线程之间发生争用时，变量集可能会动态增长以减少争用

- This class is usually preferable to AtomicLong when multiple threads update a common sum that is used for purposes such as collecting statistics, not for fine-grained synchronization control. 
- (当多个线程更新用于收集统计信息等目的(而不是用于细粒度同步控制)的公共和时，此类通常比AtomicLong更可取。)

- Under low update contention, the two classes have similar characteristics.
- But under high contention, expected throughput of this class is significantly higher, at the expense of higher space consumption.但是在高并发情况下，该类的预期吞吐量要高得多，这是以更高的空间消耗为代价的。

- This class extends Number, but does not define methods such as equals, hashCode and compareTo because instances are expected to be mutated, and so are not useful as collection keys.
  

- ————————————————————————————

- LongAdder在高并发的场景下会比它的前辈————AtomicLong 具有更好的性能，代价是消耗更多的内存空间

- AtomicLong是利用了底层的CAS操作来提供并发性的，比如addAndGet方法调用了Unsafe类的getAndAddLong方法，该方法是个native方法，它的逻辑是采用自旋的方式不断更新目标值，直到更新成功。
```
    public final long getAndAddLong(Object var1, long var2, long var4) {
        long var6;
        do {
            var6 = this.getLongVolatile(var1, var2);
        } while(!this.compareAndSwapLong(var1, var2, var6, var6 + var4));

        return var6;
    }
```
- 在并发量较低的环境下，线程冲突的概率比较小，自旋的次数不会很多。但是，高并发环境下，N个线程同时进行自旋操作，会出现大量失败并不断自旋的情况，此时AtomicLong的自旋会成为瓶颈

- 这就是LongAdder引入的初衷——解决高并发环境下AtomicLong的自旋瓶颈问题。

- (AtomicLong中有个内部变量value保存着实际的long值，所有的操作都是针对该变量进行。也就是说，高并发环境下，value变量其实是一个热点，也就是N个线程竞争一个热点。)
- LongAdder的基本思路就是分散热点，将value值分散到一个数组中，不同线程会命中到数组的不同槽中，各个线程只对自己槽中的那个值进行CAS操作，这样热点就被分散了，冲突的概率就小很多。如果要获取真正的long值，只要将各个槽中的变量值累加返回。
- 另外，从空间方面考虑，LongAdder其实是一种“空间换时间”的思想


- AtomicLong提供的功能其实更丰富，尤其是addAndGet、decrementAndGet、compareAndSet这些方法
- addAndGet、decrementAndGet除了单纯的做自增自减外，还可以立即获取增减后的值，如果业务需求需要精确的控制计数，做计数比较，AtomicLong也更合适。
- 而LongAdder则需要做同步控制才能精确获取增减后的值

- 如果并发量很多，存在大量写多读少的情况，那LongAdder可能更合适。
- 低并发、一般的业务场景下AtomicLong是足够了
