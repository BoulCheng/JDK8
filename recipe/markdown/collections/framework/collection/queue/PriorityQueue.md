# PriorityQueue

- public class PriorityQueue<E> extends AbstractQueue<E> implements Serializable

- An unbounded priority queue based on a priority heap.(数据结构-堆)

- A priority queue is unbounded, but has an internal capacity governing the size of an array used to store the elements on the queue. It is always at least as large as the queue size. As elements are added to a priority queue, its capacity grows automatically. The details of the growth policy are not specified.

- The elements of the priority queue are ordered according to their natural ordering, or by a Comparator provided at queue construction time

-  does not permit null elements

- The head of this queue is the least element with respect to the specified ordering. *If multiple elements are tied for least value, the head is one of those elements -- ties are broken arbitrarily*

- The Iterator provided in method iterator() is not guaranteed to traverse the elements of the priority queue in any particular order. If you need ordered traversal, consider using Arrays.sort(pq.toArray()).

- Implementation note: this implementation provides O(log(n)) time for the enqueuing and dequeuing methods (offer, poll, remove() and add); linear time for the remove(Object) and contains(Object) methods; and constant time for the retrieval methods (peek, element, and size).

    ```
        private void grow(int minCapacity) {
            int oldCapacity = queue.length;
            // Double size if small; else grow by 50%
            int newCapacity = oldCapacity + ((oldCapacity < 64) ?
                                             (oldCapacity + 2) :
                                             (oldCapacity >> 1));
            // overflow-conscious code
            if (newCapacity - MAX_ARRAY_SIZE > 0)
                newCapacity = hugeCapacity(minCapacity);
            queue = Arrays.copyOf(queue, newCapacity);
        }
    ```