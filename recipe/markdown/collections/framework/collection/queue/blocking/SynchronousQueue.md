# SynchronousQueue

- A synchronous queue does not have any internal capacity, not even a capacity of one.

- A blocking queue in which each insert operation must wait for a corresponding remove operation by another thread, and vice versa.

- features
    - You cannot peek at a synchronous queue because an element is only present when you try to remove it; 
    - you cannot insert an element (using any method) unless another thread is trying to remove it;
    - you cannot iterate as there is nothing to iterate.
    - The head of the queue is the element that the first queued inserting thread is trying to add to the queue;(队列的头部是第一个排队插入线程试图添加到队列中的元素;)
    - This queue does not permit null elements.
    
    
-  They are well suited for handoff designs, in which an object running in one thread must sync up with an object running in another thread in order to hand it some information, event, or task

- This class supports an optional fairness policy for ordering waiting producer and consumer threads. By default, this ordering is not guaranteed. However, a queue constructed with fairness set to true grants threads access in FIFO order.
  
