# HashMap 
- 链表的节点数大于等于8 并且 哈希表数组长度大于等于64 时链表会转化为红黑树, 
    - If the current tree appears to have too few nodes, the bin is converted back to a plain bin. (The test triggers somewhere between 2 and 6 nodes, depending on tree structure).
- 当键值对总数(size()) > (initialCapacity的最小的2的幂(数组length) * loadFactor)则进行2倍扩容；(当键值对总数 > 数组长度 * 0.75，进行2倍扩容)
- loadFactor 默认0.75 可以理解为平均每个桶装满0.75个元素就进行扩容 
- Hash table based implementation of the Map interface. (基于哈希表实现的map接口)
- 不会缩容
    - 在remove操作中做缩容操作，remove操作居然有可能是O(n)的复杂度
    - 合适的时候通知单独的线程来做缩容操作,HashMap不是线程安全的
    - 为了减少 hashmap 自动扩容次数，鼓励在创建 hashmap 对象时估算实际存储的元素数量指定初始容量
    - Java在大部分情况下都是用空间换时间的，缩容这种不符合Java的哲学
- permits null values and the null key.
    ```
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    ```

- The HashMap class is roughly equivalent to Hashtable, except that it is unsynchronized and permits nulls.




- This class makes no guarantees as to the order of the map; in particular, it does not guarantee that the order will remain constant over time.

- **This implementation provides constant-time performance for the basic operations (get and put), assuming the hash function disperses the elements properly among the buckets. Iteration over collection views requires time proportional to the "capacity" of the HashMap instance (the number of buckets) plus its size (the number of key-value mappings). Thus, it's very important not to set the initial capacity too high (or the load factor too low) if iteration performance is important.**

- An instance of HashMap has two parameters that affect its performance: initial capacity and load factor
    - capacity
        - the number of buckets in the hash table
        - and the initial capacity is simply the capacity at the time the hash table is created.(but 会找到大于等于cap的最小的2的幂)
        
    - load factor
        - a measure of how full the hash table is allowed to get before its capacity is automatically increased.
        - When the number of entries in the hash table exceeds (threshold)the product of the load factor and the current capacity, the hash table is rehashed.(approximately twice the number of buckets.)
        - As a general rule, the default load factor (.75) offers a good tradeoff between time and space costs.Higher values decrease the space overhead but increase the lookup cost
        
- If the initial capacity(注意实际是大于等于cap的最小的2的幂) is greater than the maximum number of entries divided by the load factor, no rehash operations will ever occur.

- Note that using many keys with the same hashCode() is a sure way to slow down performance of any hash table. To ameliorate impact, when keys are Comparable, this class may use comparison order among keys to help break ties.(complex)

- treeifyBin(单向链表红黑树化, 必须同时满足下面两个条件)
    - Bins are converted to trees when adding an element to a bin with at least this many nodes. (= TREEIFY_THRESHOLD = 8 即会触发树化)
        ```
        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                                    treeifyBin(tab, hash);
        ```
    - The smallest table capacity for which bins may be treeified. (Otherwise the table is resized if too many nodes in a bin.) (= MIN_TREEIFY_CAPACITY = 64 才会触发某个bin(bucket)树化)
        ```
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
                    resize();
        ```
        
        
- 哈希冲突如何解决呢？
    - 主要从两个方面考虑，
        一 方面是避免冲突，
            - 通过扰动函数来增加hashCode的随机性，避免冲突
        - 另一方面是在冲突时合理地解决冲突，尽可能提高查询效率
            - 拉链表
                - 同一key的判断逻辑 先判断hash值是否相同，再比较key的地址或值是否相同
                - 在JDK1.8之前，HashMap在并发场景下扩容时存在一个bug，形成死链，导致get该位置元素的时候，会死循环，使CPU利用率高居不下
                - JDK1.8之中，引入了高低位链表（双端链表）
                    -如 原有容量8扩容至16，将[0, 7]称为低位，[8, 15]称为高位，低位对应loHead、loTail，高位对应hiHead、hiTail
            - 红黑树
                - 使查询具备O(logn)的性能。
                - vs AVL 
                    - 两者核心的区别是AVL树追求高度平衡(在AVL树中，任一节点对应的两棵子树的最大高度差为1，因此它也被称为高度平衡树)，在插入、删除节点时，成本要高于红黑树，但也因此拥有了更好的查询性能，适用于读多写少的场景。然而，对于HashMap而言，读写操作其实难分伯仲，因此选择红黑树也算是在读写性能上的一种折中
    