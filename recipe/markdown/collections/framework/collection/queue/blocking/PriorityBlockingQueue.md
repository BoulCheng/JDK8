# PriorityBlockingQueue

- An unbounded blocking queue that uses the same ordering rules as class PriorityQueue and supplies blocking retrieval operations

- Operations on this class make no guarantees about the ordering of elements with equal priority.(该类上的操作不能保证具有同等优先级的元素的顺序。)

-  If you need to enforce an ordering, you can define custom classes or comparators that use a secondary key to break ties in primary priority values. For example, here is a class that applies first-in-first-out tie-breaking to comparable elements. To use it, you would insert a new FIFOEntry(anEntry) instead of a plain entry object.
    ```
     class FIFOEntry<E extends Comparable<? super E>>
         implements Comparable<FIFOEntry<E>> {
       static final AtomicLong seq = new AtomicLong(0);
       final long seqNum;
       final E entry;
       public FIFOEntry(E entry) {
         seqNum = seq.getAndIncrement();
         this.entry = entry;
       }
       public E getEntry() { return entry; }
       public int compareTo(FIFOEntry<E> other) {
         int res = entry.compareTo(other.entry);
         if (res == 0 && other.entry != this.entry)
           res = (seqNum < other.seqNum ? -1 : 1);
         return res;
       }
     }
    ```