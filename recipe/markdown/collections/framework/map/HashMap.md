# HashMap 
- 链表的节点数大于等于8 并且 哈希表数组长度大于等于64 时链表会转化为红黑树, 
    - If the current tree appears to have too few nodes, the bin is converted back to a plain bin. (The test triggers somewhere between 2 and 6 nodes, depending on tree structure).
- 当键值对总数 > 数组长度 * 0.75，进行2倍扩容，注意不会缩容。
 
- Hash table based implementation of the Map interface. (基于哈希表实现的map接口)

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
        
        
- 