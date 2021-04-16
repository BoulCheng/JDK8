# LinkedBlockingQueue

- An optionally-bounded blocking queue based on linked nodes.

- This queue orders elements FIFO (first-in-first-out). 

- Linked queues typically have higher throughput than array-based queues but less predictable performance in most concurrent applications.

- add()并发安全  offer()是并发安全的但不阻塞

- put、take独立一个ReentrantLock

- 新增节点会新增Node对象(ArrayBlockingQueue不会)

- 单向链表，LinkedBlockingDeque是双向链表
    ```
        /**
         * Linked list node class
         */
        static class Node<E> {
            E item;
    
            /**
             * One of:
             * - the real successor Node
             * - this Node, meaning the successor is head.next
             * - null, meaning there is no successor (this is the last node)
             */
            Node<E> next;
    
            Node(E x) { item = x; }
        }
    ```
    
- character
    - ReentrantLock 非公平锁
    - put、take独立一个ReentrantLock
    - 大小无界
    - 高吞吐量