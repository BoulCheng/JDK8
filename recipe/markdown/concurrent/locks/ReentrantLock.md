# ReentrantLock
- A reentrant mutual exclusion Lock with the same basic behavior and semantics as the implicit monitor lock accessed using synchronized methods and statements, but with extended capabilities.(一种可重入互斥锁，具有与使用同步方法和语句访问的隐式监视器锁相同的基本行为和语义，但具有扩展功能。)

- A ReentrantLock is owned by the thread last successfully locking, but not yet unlocking it.(ReentrantLock归最后一个成功锁定的线程所有，但还没有解锁它。)

- The constructor for this class accepts an optional fairness parameter.
    - When set true, under contention, locks favor granting access to the longest-waiting thread. 
    - Otherwise this lock does not guarantee any particular access order. 
    
    - Programs using fair locks accessed by many threads may display lower overall throughput (i.e., are slower; often much slower) than those using the default setting, but have smaller variances in times to obtain locks and guarantee lack of starvation. 
    - (使用多个线程访问的公平锁的程序可能会显示较低的总体吞吐量(例如，更慢;通常比使用默认设置要慢得多))
    
    - Note however, that fairness of locks does not guarantee fairness of thread scheduling. Thus, one of many threads using a fair lock may obtain it multiple times in succession while other active threads are not progressing and not currently holding the lock
    - (但是请注意，锁的公平性并不保证线程调度的公平性。因此，使用公平锁的多个线程中的一个可能会连续多次获得公平锁，而其他活动线程没有进展，也没有当前持有锁)
    
    - Also note that the untimed tryLock() method does not honor the fairness setting. It will succeed if the lock is available even if other threads are waiting.
    
    
- It is recommended practice to always immediately follow a call to lock with a try block, most typically in a before/after construction such as:
    ```
     class X {
       private final ReentrantLock lock = new ReentrantLock();
       // ...
    
       public void m() {
         lock.lock();  // block until condition holds
         try {
           // ... method body
         } finally {
           lock.unlock()
         }
       }
     }
    
    ```  

- In addition to implementing the Lock interface, this class defines a number of public and protected methods for inspecting the state of the lock. Some of these methods are only useful for instrumentation and monitoring.
- (除了实现锁接口之外，该类还定义了一些用于检查锁状态的公共和受保护的方法。其中一些方法仅用于检测和监视。)  

- Serialization of this class behaves in the same way as built-in locks: a deserialized lock is in the unlocked state, regardless of its state when serialized.
- (该类的序列化与内置锁的行为方式相同:反序列化锁处于解锁状态，而与序列化时锁的状态无关。)

- This lock supports a maximum of 2147483647 recursive locks by the same thread. Attempts to exceed this limit result in Error throws from locking methods.
- (此锁最多支持同一线程的2147483647个递归锁。试图超过此限制将导致锁定方法抛出错误。)



