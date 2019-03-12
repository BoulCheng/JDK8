# LinkedBlockingDeque

- An optionally-bounded blocking deque based on linked nodes.

- Most operations run in constant time (ignoring time spent blocking). Exceptions include remove, removeFirstOccurrence, removeLastOccurrence, contains, iterator.remove(), and the bulk operations, all of which run in linear time.
  
- 所有操作共用一个ReentrantLock

- 双向链表
    ```
    
        /** Doubly-linked list node class */
        static final class Node<E> {
            /**
             * The item, or null if this node has been removed.
             */
            E item;
    
            /**
             * One of:
             * - the real predecessor Node
             * - this Node, meaning the predecessor is tail
             * - null, meaning there is no predecessor
             */
            Node<E> prev;
    
            /**
             * One of:
             * - the real successor Node
             * - this Node, meaning the successor is head
             * - null, meaning there is no successor
             */
            Node<E> next;
    
            Node(E x) {
                item = x;
            }
        }
    ```