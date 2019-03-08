# Hashtable

- Hashtable is synchronized. 

- If a thread-safe implementation is not needed, it is recommended to use HashMap in place of Hashtable. 

- If a thread-safe highly-concurrent implementation is desired, then it is recommended to use ConcurrentHashMap in place of Hashtable.

- Any non-null object can be used as a key or as a value.

- As of the Java 2 platform v1.2, this class was retrofitted to implement the Map interface, making it a member of the Java Collections Framework. 

- capacity 
    - the number of buckets in the hash table
    - Note that the hash table is open: in the case of a "hash collision", a single bucket stores multiple entries, which must be searched sequentially. 
    - The initial capacity controls a tradeoff between wasted space and the need for rehash operations, which are time-consuming
    - No rehash operations will ever occur if the initial capacity is greater than the maximum number of entries the Hashtable will contain divided by its load factor. 
    - However, setting the initial capacity too high can waste space.
    - If many entries are to be made into a Hashtable, creating it with a sufficiently large capacity may allow the entries to be inserted more efficiently than letting it perform automatic rehashing as needed to grow the table.
    
- load factor
    - a measure of how full the hash table is allowed to get before its capacity is automatically increased.
    - Generally, the default load factor (.75) offers a good tradeoff between time and space costs. Higher values decrease the space overhead but increase the time cost to look up an entry

- To successfully store and retrieve objects from a hashtable, the objects used as keys must implement the hashCode method and the equals method.

