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
    
- 最小堆：根节点是优先级最低的节点


- 最大堆
```


import java.util.AbstractQueue;
import java.util.Iterator;

public class MaxPriorityQueue<E> extends AbstractQueue<E> {

    private int capacity;
    private Object[] queue;
    private int size;


    public MaxPriorityQueue(int size) {
        this.capacity = size;
        this.queue = new Object[size];
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean offer(E e) {
        if (size >= capacity) {
            return false;
        }
        if (size == 0) {
            queue[0] = e;
        } else {
            siftUp(size, e);
        }
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    private void siftUp(int k, E e) {
        Comparable<E> key = (Comparable<E>) e;
        while (k > 0) {
            int i = (k - 1) >>> 1;
            E parent = (E) queue[i];
            if (key.compareTo(parent) <= 0) {
                break;
            } else {
                queue[k] = parent;
                k = i;
            }
        }
        queue[k] = e;
    }


    @SuppressWarnings("unchecked")
    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        E e = (E) queue[0];
        queue[0] = null;
        int k = --size;
        E last = (E) queue[k];
        queue[k] = null;


        //必须在调用前先维护size
        //最后一个元素不需要在调用siftDown

        if (k != 0) {
            siftDown(0, last);
        }

        return e;
    }


    /**
     *         //必须在调用前先维护size
     *         //最后一个元素不需要在调用siftDown
     * @param k
     * @param e
     */
    @SuppressWarnings("unchecked")
    private void siftDown(int k, E e) {
        //最后一个叶子节点
        //也可以采用 PriorityQueue 的 siftDown方式， size >>> 1 即使是根节点计算出来的父结点也是本身, 在while循环时通过小于而不是小于等可以统一处理只剩一个结点的情况 即只剩一个结点时不会进入while循环
        if ((size - 1) == 0) {
            queue[k] = e;
            return;
        }
        int lastNonLeaf = ((size - 1) - 1) >>> 1;
        Comparable<E> key = (Comparable<E>) e;

        //遍历到最后一个非叶子节点
        while (k <= lastNonLeaf) {
            int i = (k << 1) + 1;
            E x = (E) queue[i];
            int right = i + 1;

            //最后一个非叶子节点可能没有右孩子但一定会有左孩子
            if (right < size && ((Comparable<E>) queue[right]).compareTo(x) > 0) {
                i = right;
                x = (E) queue[right];
            }

            if (key.compareTo(x) >= 0) {
                break;
            } else {
                queue[k] = x;
                k = i;
            }
        }

        queue[k] = e;
    }


    @Override
    public E peek() {
        if (size == 0) {
            return null;
        }
        return (E)queue[0];
    }


    public static void main(String[] args) {

        MaxPriorityQueue<Integer> maxPriorityQueue = new MaxPriorityQueue<>(10);
        maxPriorityQueue.offer(1);
        maxPriorityQueue.offer(3);
        maxPriorityQueue.offer(4);
        maxPriorityQueue.offer(9);
        maxPriorityQueue.offer(0);
        maxPriorityQueue.offer(7);
        maxPriorityQueue.offer(5);
        maxPriorityQueue.offer(1110);
        maxPriorityQueue.offer(110);
        maxPriorityQueue.offer(100);
        maxPriorityQueue.offer(200);
        maxPriorityQueue.offer(19);

        while (maxPriorityQueue.peek() != null) {
            System.out.println(maxPriorityQueue.poll());
        }
    }




}

```