
- TransferQueue队列实现不想让生产者过剩产生大量消息场景非常有用，避免产生OutOfMemory 错误。这样即由消费者决定生产者的速度。
- 生产者和消费者有序协调进行工作



- TransferQueue则更进一步，生产者会一直阻塞直到所添加到队列的元素被某一个消费者所消费（不仅仅是添加到队列里就完事）。新添加的transfer方法用来实现这种约束。顾名思义，阻塞就是发生在元素从一个线程transfer到另一个线程的过程中，它有效地实现了元素在线程之间的传递（以建立Java内存模型中的happens-before关系的方式）
- TransferQueue相比SynchronousQueue用处更广、更好用，因为你可以决定是使用BlockingQueue的方法（译者注：例如put方法）还是确保一次传递完成（译者注：即transfer方法）。在队列中已有元素的情况下，调用transfer方法，可以确保队列中被传递元素之前的所有元素都能被处理。
```
public void transfer(E e)
              throws InterruptedException
Transfers the element to a consumer, waiting if necessary to do so.
More precisely, transfers the specified element immediately if there exists a consumer already waiting to receive it (in take() or timed poll), else inserts the specified element at the tail of this queue and waits until the element is received by a consumer.
```

- Doug Lea说从功能角度来讲，LinkedTransferQueue实际上是ConcurrentLinkedQueue、SynchronousQueue（公平模式）和LinkedBlockingQueue的超集。而且LinkedTransferQueue更好用，因为它不仅仅综合了这几个类的功能，同时也提供了更高效的实现。
    - 性能测试的结果表明它优于Java 5的那些类（译者注：ConcurrentLinkedQueue、SynchronousQueue和LinkedBlockingQueue）。LinkedTransferQueue的性能分别是SynchronousQueue的3倍（非公平模式）和14倍（公平模式）。因为像ThreadPoolExecutor这样的类在任务传递时都是使用SynchronousQueue，所以使用LinkedTransferQueue来代替SynchronousQueue也会使得ThreadPoolExecutor得到相应的性能提升。考虑到executor在并发编程中的重要性，你就会理解添加这个实现类的重要性了