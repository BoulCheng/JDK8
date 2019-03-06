# Lock

- A lock is a tool for controlling access to a shared resource by multiple threads
- Lock implementations provide more extensive locking operations than can be obtained using synchronized methods and statements. 
- They allow more flexible structuring, may have quite different properties, and may support multiple associated Condition objects.

- Commonly, a lock provides exclusive access to a shared resource. However, some locks may allow concurrent access to a shared resource, such as the read lock of a ReadWriteLock.

- synchronized
    - The use of synchronized methods or statements provides access to the implicit monitor lock associated with every object,
    - but forces all lock acquisition and release to occur in a block-structured way. and all locks must be released in the same lexical scope in which they were acquired.
    - when multiple locks are acquired they must be released in the opposite order
    - the automatic release of locks that occurs with synchronized methods and statements.

- Lock
    - Implementations of the Lock interface allow a lock to be acquired and released in different scopes. care must be taken to ensure that all code that is executed while the lock is held is protected by try-finally or try-catch to ensure that the lock is released when necessary.
    - and allowing multiple locks to be acquired and released in any order.
    
- by providing a non-blocking attempt to acquire a lock (tryLock()), an attempt to acquire the lock that can be interrupted (lockInterruptibly(), and an attempt to acquire the lock that can timeout (tryLock(long, TimeUnit)).
    
- A Lock class can also provide behavior and semantics that is quite different from that of the implicit monitor lock, such as guaranteed ordering, non-reentrant usage, or deadlock detection. If an implementation provides such specialized semantics then the implementation must document those semantics.
  
- Note that Lock instances are just normal objects and can themselves be used as the target in a synchronized statement. It is recommended that to avoid confusion you never use Lock instances in this way, except within their own implementation.

- **complex**
    - Memory Synchronization

- The three forms of lock acquisition (interruptible, non-interruptible, and timed) may differ in their performance characteristics, ordering guarantees, or other implementation qualities
- Further, the ability to interrupt the ongoing acquisition of a lock may not be available in a given Lock class. Consequently, an implementation is not required to define exactly the same guarantees or semantics for all three forms of lock acquisition, nor is it required to support interruption of an ongoing lock acquisition

- As interruption generally implies cancellation, and checks for interruption are often infrequent, an implementation can favor responding to an interrupt over normal method return.

- This is true even if it can be shown that the interrupt occurred after another action may have unblocked the thread. An implementation should document this behavior.

- Method
    - lock() : If the lock is not available then the current thread becomes disabled for thread scheduling purposes and lies dormant until the lock has been acquired.
    - lockInterruptibly() : Acquires the lock unless the current thread is interrupted. The ability to interrupt a lock acquisition in some implementations may not be possible, and if possible may be an expensive operation
    - tryLock() : Acquires the lock only if it is free at the time of invocation.
    - tryLock(long, TimeUnit) : Acquires the lock if it is free within the given waiting time and the current thread has not been interrupted. (InterruptedException is thrown and the current thread's interrupted status is cleared if interruption of lock acquisition is supported when interrupted). The ability to interrupt a lock acquisition in some implementations may not be possible, and if possible may be an expensive operation.
    - unlock() : A Lock implementation will usually impose restrictions on which thread can release a lock (typically only the holder of the lock can release it) 
    - newCondition() : Returns a new Condition instance that is bound to this Lock instance. Before waiting on the condition the lock must be held by the current thread. A call to Condition.await() will atomically release the lock before waiting and re-acquire the lock before the wait returns.

    