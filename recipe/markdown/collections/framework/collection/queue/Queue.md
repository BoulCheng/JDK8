# Queue

- The Queue interface does not define the blocking queue methods, which are common in concurrent programming. These methods, which wait for elements to appear or for space to become available, are defined in the BlockingQueue interface, which extends this interface.
  
- A collection designed for holding elements prior to processing

- The latter form(returns a special value (either null or false)) of the insert operation is designed specifically for use with capacity-restricted Queue implementations; in most implementations, insert operations cannot fail.

- **Summary of Queue methods**

- *order*
    - Queues typically, but do not necessarily, order elements in a FIFO (first-in-first-out) manner.
    - Among the exceptions are priority queues, which order elements according to a supplied comparator, or the elements' natural ordering,
    - and LIFO queues (or stacks) which order the elements LIFO (last-in-first-out) 
    - In a FIFO queue, all new elements are inserted at the tail of the queue. Other kinds of queues may use different placement rules.
    - The offer method is designed for use when failure is a normal, rather than exceptional occurrence, for example, in fixed-capacity (or "bounded") queues.
    - The remove() and poll() methods remove and return the head of the queue. Exactly which element is removed from the queue is a function of the queue's ordering policy, which differs from implementation to implementation.
    - The element() and peek() methods return, but do not remove, the head of the queue.
      
- Queue implementations generally do not define element-based versions of methods equals and hashCode but instead inherit the identity based versions from class Object, because element-based equality is not always well-defined for queues with the same elements but different ordering properties.
  
