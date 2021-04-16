# DelayQueue

- 通过getDelay返回小于等于0判断为过期 过期的元素可以取出 否则等待有过期的元素
- 内部采用优先队列 PriorityQueue 存储元素，Delayed 接口继承了Comparable接口，插入元素通过Comparable#compareTo调用Delayed#getDelay判断不同元素过期的快慢再放入优先级队列
- An unbounded blocking queue of Delayed elements, in which an element can only be taken when its delay has expired.

- Expiration occurs when an element's getDelay(TimeUnit.NANOSECONDS) method returns a value less than or equal to zero.

- The head of the queue is that Delayed element whose delay expired furthest in the past. If no delay has expired there is no head and poll will return null

- Even though unexpired elements cannot be removed using take or poll, they are otherwise treated as normal elements. For example, the size method returns the count of both expired and unexpired elements. This queue does not permit null elements.

- 内部采用优先队列 PriorityQueue 存储元素, 元素必须实现Delayed接口，Delayed接口继承了Comparable接口

- public class DelayQueue<E extends Delayed> extends AbstractQueue<E> implements BlockingQueue<E>

```
private final PriorityQueue<E> q = new PriorityQueue<E>();
```

```
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            for (;;) {
                E first = q.peek();
                if (first == null)
                    available.await();
                else {
                    long delay = first.getDelay(NANOSECONDS);
                    if (delay <= 0)
                        return q.poll();
                    first = null; // don't retain ref while waiting
                    if (leader != null)
                        available.await();
                    else {
                        Thread thisThread = Thread.currentThread();
                        leader = thisThread;
                        try {
                            available.awaitNanos(delay);
                        } finally {
                            if (leader == thisThread)
                                leader = null;
                        }
                    }
                }
            }
        } finally {
            if (leader == null && q.peek() != null)
                available.signal();
            lock.unlock();
        }
    }
```