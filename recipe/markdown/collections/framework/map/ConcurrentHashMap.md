# ConcurrentHashMap

- 并发安全的保证
    - put
        - 可以保证hash值相同的key在第一次put时的并发安全，即假如key1.hash = key2.hash 那么key1、key2并发put不会出现key1覆盖key2 或者 key2覆盖key1，覆盖是指key覆盖不是value覆盖
        - 当调用ConcurrentHashMap#putIfAbsent 可以保证同一个key在第一次put时的并发安全，即初始的value不会发生相互覆盖的情况，
        - 但如果不是 putIfAbsent ，不能保证同一个key在第一次put时的并发安全，即初始的value可能会发生相互覆盖的情况
        - hashmap##put(Object, Object) 、ConcurrentHashMap##put(Object, Object) 都可以更新同一个key的value
        - 对比关键源码
            - hashmap
                ```
              // hash值相同的key在第一次put时的可能会产生key相互覆盖
                        if ((p = tab[i = (n - 1) & hash]) == null)
                            tab[i] = newNode(hash, key, value, null);
                ```
            - ConcurrentHashMap
                ```
              // cas 更新， hash值相同的key在第一次put时的不会产生key相互覆盖
                            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
                                if (casTabAt(tab, i, null,
                                             new Node<K,V>(hash, key, value, null)))
                                    break;                   // no lock when adding to empty bin
                            }
                ```
                ```
                                synchronized (f) {
                                    if (tabAt(tab, i) == f) {
                                        if (fh >= 0) {
                                            binCount = 1;
                                            for (Node<K,V> e = f;; ++binCount) {
                                                K ek;
                                                if (e.hash == hash &&
                                                    ((ek = e.key) == key ||
                                                     (ek != null && key.equals(ek)))) {
                                                    oldVal = e.val;
              // 如果是 putIfAbsent，产生并发更新冲突是，cas 更新失败，继续while循环，执行put，执行到这里并不会更新
              // 但如果不是 putIfAbsent ，不能保证同一个key在第一次put时的并发安全，即初始的value可能会发生相互覆盖的情况
              
                                                    if (!onlyIfAbsent)
                                                        e.val = value;
                                                    break;
                                                }
                                                Node<K,V> pred = e;
                                                if ((e = e.next) == null) {
                                                    pred.next = new Node<K,V>(hash, key,
                                                                              value, null);
                                                    break;
                                                }
                                            }
                                        }
                                        else if (f instanceof TreeBin) {
                                            Node<K,V> p;
                                            binCount = 2;
                                            if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                                           value)) != null) {
                                                oldVal = p.val;
                                                if (!onlyIfAbsent)
                                                    p.val = value;
                                            }
                                        }
                                    }
                                }
                ```
- 扩容

- 本身已经保证键值对总数(size()) 不超过 initialCapacity 的情况且正常情况下不会进行扩容 (任意loadFactor)
- 链表的节点数大于等于8 并且 哈希表数组长度大于等于64 时链表会转化为红黑树 

- 当键值对总数 > 数组长度 * 0.75，进行2倍扩容，注意不会缩容。

- 遍历的结果只能是遍历开始时已经完成的更新操作的结果，一个key的更新操作先行发生于读取该key本次更新目标值的操作。

- A hash table supporting full concurrency of retrievals and high expected concurrency for updates.

- even though all operations are thread-safe, retrieval operations do not entail locking, and there is not any support for locking the entire table in a way that prevents all access. This class is fully interoperable with Hashtable in programs that rely on its thread safety but not on its synchronization details.

- Retrieval operations (including get) generally do not block, so may overlap with update operations (including put and remove).

- Retrievals reflect the results of the most recently completed update operations holding upon their onset. (More formally, an update operation for a given key bears a happens-before relation with any (non-null) retrieval for that key reporting the updated value.) 

- Similarly, Iterators, Spliterators and Enumerations return elements reflecting the state of the hash table at some point at or since the creation of the iterator/enumeration. They do not throw ConcurrentModificationException. However, iterators are designed to be used by only one thread at a time. 

- For aggregate operations such as putAll and clear, concurrent retrievals may reflect insertion or removal of only some entries

- Bear in mind that the results of aggregate status methods including size, isEmpty, and containsValue are typically useful only when a map is not undergoing concurrent updates in other threads

- Also, for compatibility with previous versions of this class, constructors may optionally specify an expected concurrencyLevel as an additional hint for internal sizing

- Note that using many keys with exactly the same hashCode() is a sure way to slow down performance of any hash table. To ameliorate impact, when keys are Comparable, this class may use comparison order among keys to help break ties.

- Like Hashtable but unlike HashMap, this class does not allow null to be used as a key or value.

- ConcurrentHashMaps support a set of sequential and parallel bulk operations that, unlike most Stream methods, are designed to be safely, and often sensibly, applied even with maps that are being concurrently updated by other threads. There are three kinds of operation(i.e., forEach, search, reduce), each with four forms, accepting functions with Keys, Values, Entries, and (Key, Value) arguments and/or return values.

- Because the elements of a ConcurrentHashMap are not ordered in any particular way, and may be processed in different orders in different parallel executions, the correctness of supplied functions should not depend on any ordering, or on any other objects or values that may transiently change while computation is in progress; and except for forEach actions, should ideally be side-effect-free. Bulk operations on Map.Entry objects do not support method setValue.

- These bulk operations accept a parallelismThreshold argument. Methods proceed sequentially if the current map size is estimated to be less than the given threshold. Using a value of Long.MAX_VALUE suppresses all parallelism.(i.e., 当size被估计小于parallelismThreshold时顺序执行而不并行执行，parallelismThreshold=Long.MAX_VALUE时所有操作都将顺序执行). Using a value of 1 results in maximal parallelism by partitioning into enough subtasks to fully utilize the ForkJoinPool.commonPool() that is used for all parallel computations. Normally, you would initially choose one of these extreme values, and then measure performance of using in-between values that trade off overhead versus throughput.

- The concurrency properties of bulk operations follow from those of ConcurrentHashMap: Any non-null result returned from get(key) and related access methods bears a happens-before relation with the associated insertion or update. Conversely, because keys and values in the map are never null, null serves as a reliable atomic indicator of the current lack of any result. To maintain this property, null serves as an implicit basis for all non-scalar reduction operations

- Search and transformation functions provided as arguments should similarly return null to indicate the lack of any result (in which case it is not used).

- Bulk operations may complete abruptly, throwing an exception encountered in the application of a supplied function. Bear in mind when handling such exceptions that other concurrently executing functions could also have thrown exceptions, or would have done so if the first exception had not occurred.
  
- Speedups for parallel compared to sequential forms are common but not guaranteed. Parallel operations involving brief functions on small maps may execute more slowly than sequential forms if the underlying work to parallelize the computation is more expensive than the computation itself. Similarly, parallelization may not lead to much actual parallelism if all processors are busy performing unrelated tasks.

- All arguments to all task methods must be non-null.
  
- **complex**
    - i.e., keys that have distinct hash codes but fall into the same slot modulo the table size
    - The table is dynamically expanded when there are too many collisions, with the expected average effect of maintaining roughly two bins per mapping.
    
- **todo**
    - LongAdder
    - Set projection
    - ForkJoinPool.commonPool()
    - addCount()
    - new TreeBin<K,V>()
    - transfer()
    - Unsafe
    
    

- 1 0000
- 1111    