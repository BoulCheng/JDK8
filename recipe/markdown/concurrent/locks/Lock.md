# Lock

- synchronized
    - but forces all lock acquisition and release to occur in a block-structured way. and all locks must be released in the same lexical scope in which they were acquired.
    - 锁的获取和释放在同一个块结构中，释放和获取在同一个作用域，自动释放
    - when multiple locks are acquired they must be released in the opposite order
    - 多个锁的释放需要按获取锁的相反顺序
    
    - 确认是否是公平锁？

- Lock
    - Implementations of the Lock interface allow a lock to be acquired and released in different scopes. care must be taken to ensure that all code that is executed while the lock is held is protected by try-finally or try-catch to ensure that the lock is released when necessary.
    - 允许锁的获取和释放在不同的作用域
    - and allowing multiple locks to be acquired and released in any order.
    - 允许以任何顺序获取和释放多个锁。
    - A Lock class can also provide behavior and semantics that is quite different from that of the implicit monitor lock, 
    - such as guaranteed ordering, non-reentrant usage, or deadlock detection. If an implementation provides such specialized semantics then the implementation must document those semantics.
    

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
    - 锁未获得释放会发生什么 会抛出IllegalMonitorStateException. 只有占有锁的线程才可以释放锁
        ```
        if (Thread.currentThread() != getExclusiveOwnerThread())
                        throw new IllegalMonitorStateException();
        ```

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


#### Lock vs synchronized 
- 分组唤醒(Condition)
    - 一个Lock可以生成多个Condition实现分组等待唤醒，当 Object#wait Object#notify不能；    
- 读写锁 链锁 非阻塞锁 可中断锁 可超时锁 公平锁 都是可重入锁 
- Lock接口的实现允许在不同作用域内获取和释放锁，并且允许以任意的次序获取和释放多个锁
- 所有的{Lock接口的实现}都必须提供与内置的监视器锁(Java内存模型中定义的)相同的内存同步语义：
    - 一个成功的{lock.lock()}操作都必须与Lock操作拥有一致的内存同步效果。
    - 一个成功的{lock.unlock()}操作都必须与Unlock操作拥有一致的内存同步效果。
    - 不成功的加锁或解锁操作以及可重入的加锁和解锁操作，不需要保证任何内存同步效果
1.JDK版本不同

synchronized关键字产生于JKD1.5之前，是低版本保证共享资源同步访问的主要技术。
Lock接口产生于JDK1.5版本，位于著名的java.util.concurrent并发包中，是Java提供的一种比synchronized关键字更加灵活与丰富的共享资源同步访问技术。
2.读写锁

synchronized关键字只提供了一种锁，即独占锁。
Lock接口不仅提供了与前者类似的独占锁，而且还通过ReadWriteLock接口提供了读锁和写锁。
读写锁最大的优势在于读锁与读锁并不独占，提高了共享资源的使用效率。
3.块锁与链锁

synchronized关键字以代码块或者说是作用域机制实现了加锁与解锁，我简称为块锁。synchronized关键字的作用域机制导致同步块必须包含在同一方法中，且多个锁的加锁与解锁顺序正好相反，即：{{{}}}结构。
Lock接口并不限制锁的作用域和加解锁次序，可以提供类似于链表样式的锁，所以我简称为链锁。Lock接口并不需要把加锁和解锁方法放在同一方法中，且加锁和解锁顺序完全随意，即：{{}{}}结构。
4.解锁方式

synchronized关键字：随着同步块/方法执行完毕，自动解锁。
Lock接口：需要手动通过lock.unlock()方法解锁，一般此操作位于finally{}中。
5.阻塞锁与非阻塞锁

synchronized关键字提供的锁是阻塞的，它会一直尝试通过轮询去获取对象的监视锁。
Lock接口通过lock.tryLock()方法提供了一种非阻塞的锁，它会尝试去获取锁，如果没有获取锁，则不再尝试。
6.可中断锁

synchronized关键字提供的锁是不可中断的，它会一直尝试去获取锁，我们无法手动的中断它。
Lock接口通过lock.lockInterruptibly()提供了一种可中断的锁，我们可以主动的去中断这个锁，避免死锁的发生。
7.可超时锁

synchronized关键字提供的锁是不可超时的，它会一直尝试去获取锁，直至获取锁。
Lock接口通过{tryLock(long, TimeUnit)方法}方法提供了一种可超时的锁，它会在一段时间内尝试去获取锁，如果限定时间超时，则不再尝试去获取锁，避免死锁的发生。
8.公平锁(线程次序保证)

我们都知道，如果高并发环境下多个线程尝试去访问同一共享资源，同一时刻只有一个线程拥有访问这个共享资源的锁，其他的线程都在等待。

synchronized关键字提供的锁是非公平锁，如果持有锁的线程释放了锁，则新进入的线程与早就等待的线程拥有同样的机会获取这个锁，简单来说就是不讲究：先来后到，反而讲究：来得早不如来得巧。非公平锁可能导致某些线程永远都不会获取锁。
Lock接口默认也是非公平锁，但是他还可以通过fair参数指定为公平锁。在公平锁机制下，等待的线程都会被标记等待次数，等待次数越多的锁获取锁的优先级越高，也就是常说的：先到先得。
9.互不干扰、可以共用

synchronized关键字是通过关键字实现对对象的加锁与解锁的。
Lock接口是通过Lock接口的实现类的实例对象的lock()和unlock()方法实现加锁与解锁的。
我们也可以通过synchronized关键字对Lock接口的实现类的实例对象进行监视器锁的加锁与解锁。而且对监视器锁的加锁与解锁与Lock接口的实现类的实例对象的lock()和unlock()方法并不冲突。
也就是说我们可以同时使用Lock接口和synchronized关键字实现同步访问控制。

- 分组唤醒
```
public class TestAASuf {

    /**
     * 分组唤醒
     * ReentrantLock 可以锁绑定多个条件Condition：synchronized 没有这个功能，ReentrantLock 用来实现分组唤醒需要唤醒的线程，可以精确唤醒，而不是像 synchronized 似的随即唤醒一个或者全部唤醒
     */
   static class ShareRes {
        // Lock 和synchronized 有相同的内存同步语义 可以保证可见性 有序性 原子性
        private int num = 1; //A:1,B:2,C:3
        private ReentrantLock lock = new ReentrantLock();
        private Condition conditionA = lock.newCondition();
        private Condition conditionB = lock.newCondition();
        private Condition conditionC = lock.newCondition();

        void print1() {
            lock.lock();
            try {
                while (num != 1) {
                    conditionA.await();
                }
                for (int i = 1; i <= 1; i++) {
                    System.out.println(Thread.currentThread().getName() + "  " + i);
                }
                num = 2;
                conditionB.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        void print2() {
            lock.lock();
            try {
                while (num != 2) {
                    conditionB.await();
                }
                for (int i = 1; i <= 2; i++) {
                    System.out.println(Thread.currentThread().getName() + "  " + i);
                }
                num = 3;
                conditionC.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        void print3() {
            lock.lock();
            try {
                while (num != 3) {
                    conditionC.await();
                }
                for (int i = 1; i <= 3; i++) {
                    System.out.println(Thread.currentThread().getName() + "  " + i);
                }
                num = 1;
                conditionA.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }



   static class LockTraditionDemo {

        public static void main(String[] args) {
            final TestAASuf.ShareRes res = new TestAASuf.ShareRes();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 30; i++) {
                        res.print1();
                    }
                }
            }, "线程A").start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 30; i++) {
                        res.print2();
                    }
                }
            }, "线程B").start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 30; i++) {
                        res.print3();
                    }
                }
            }, "线程C").start();
        }
    }
}



```


- 