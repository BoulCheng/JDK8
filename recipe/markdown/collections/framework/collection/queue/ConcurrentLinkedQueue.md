# ConcurrentLinkedQueue

- public class ConcurrentLinkedQueue<E> extends AbstractQueue<E> implements Queue<E>, Serializable

- An unbounded thread-safe queue based on linked nodes

- this class does not permit the use of null elements.

- This queue orders elements FIFO (first-in-first-out). The head of the queue is that element that has been on the queue the longest time. The tail of the queue is that element that has been on the queue the shortest time.
  
- Iterators are weakly consistent, returning elements reflecting the state of the queue at some point at or since the creation of the iterator

- the size method is NOT a constant-time operation. 
