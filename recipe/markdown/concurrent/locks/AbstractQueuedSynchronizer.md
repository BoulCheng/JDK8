# AbstractQueuedSynchronizer
- tip
    - (该抽象类的子类应该定义为(某个类-如Lock锁的实现类)非公共内部帮助器类，用于实现其封闭类的同步属性。)
    - This class supports either or both a default exclusive mode and a shared mode.(该类支持默认独占模式和共享模式中的一种或两种)
            - When acquired in exclusive mode, attempted acquires by other threads cannot succeed.
            - Shared mode acquires by multiple threads may (but need not) succeed.
    - This class defines a nested AbstractQueuedSynchronizer.ConditionObject class that can be used as a Condition implementation by subclasses supporting exclusive mode for which method isHeldExclusively() reports whether synchronization is exclusively held with respect to the current thread, method release(int) invoked with the current getState() value fully releases this object, and acquire(int), given this saved state value, eventually restores this object to its previous acquired state.
    
    
- Provides a framework for implementing blocking locks and related synchronizers (semaphores, events, etc) that rely on first-in-first-out (FIFO) wait queues
- (提供一个框架来实现依赖于先进先出(FIFO)等待队列的阻塞锁和相关同步器(信号量、事件等))

- This class is designed to be a useful basis for most kinds of synchronizers that rely on a single atomic int value to represent state
-(该类被设计为大多数依赖于单个原子int值来表示状态的同步器的有用基础)

- Subclasses must define the protected methods that change this state, and which define what that state means in terms of this object being acquired or released. 
- (子类必须定义更改此状态的受保护方法，并定义该状态对于正在获取或释放的对象的含义。)

- Subclasses can maintain other state fields, but only the atomically updated int value manipulated using methods getState(), setState(int) and compareAndSetState(int, int) is tracked with respect to synchronization.
- (子类可以维护其他状态字段，但是只有使用getState()、setState(int)和compareAndSetState(int, int)方法原子更新的int值才会根据同步跟踪。)

- Given these, the other methods in this class carry out all queuing and blocking mechanics.(鉴于此，该类中的其他方法执行所有排队和阻塞机制。)

- usage
    - Subclasses should be defined as non-public internal helper classes that are used to implement the synchronization properties of their enclosing class. 
    - (该抽象类的子类应该定义为(某个类-如Lock锁的实现类)非公共内部帮助器类，用于实现其封闭类的同步属性。)
    
    - Instead it defines methods such as acquireInterruptibly(int) that can be invoked as appropriate by concrete locks and related synchronizers to implement their public methods(即Lock#lockInterruptibly()).
    - (相反，它定义了像acquireInterruptibly(int)这样的方法，可以通过具体的锁和相关的同步器按需调用这些方法来实现它们的公共方法。)
    
    - Class AbstractQueuedSynchronizer does not implement any synchronization interface. 

- mode
    - This class supports either or both a default exclusive mode and a shared mode.(该类支持默认独占模式和共享模式中的一种或两种)
    - When acquired in exclusive mode, attempted acquires by other threads cannot succeed.
    - Shared mode acquires by multiple threads may (but need not) succeed.
    
    - This class does not "understand" these differences except in the mechanical sense that when a shared mode acquire succeeds, the next waiting thread (if one exists) must also determine whether it can acquire as well. 
    - (这个类并不“理解”这些差异，除非从机械意义上讲，当共享模式获取成功时，下一个等待的线程(如果存在)也必须确定它是否也可以获取。)
    
    - Threads waiting in the different modes share the same FIFO queue.
    
    - Usually, implementation subclasses support only one of these modes, but both can come into play for example in a ReadWriteLock. Subclasses that support only exclusive or only shared modes need not define the methods supporting the unused mode.
    
- AbstractQueuedSynchronizer.ConditionObject
    - This class defines a nested AbstractQueuedSynchronizer.ConditionObject class that can be used as a Condition implementation by subclasses supporting exclusive mode for which method isHeldExclusively() reports whether synchronization is exclusively held with respect to the current thread, method release(int) invoked with the current getState() value fully releases this object, and acquire(int), given this saved state value, eventually restores this object to its previous acquired state. 
    - (支持独占模式的AbstractQueuedSynchronizer子类用作Condition的实现类. release(getState())才会彻底释放锁). acquire(int)保存state状态)
    - No AbstractQueuedSynchronizer method otherwise creates such a condition, so if this constraint cannot be met, do not use it.
    - The behavior of AbstractQueuedSynchronizer.ConditionObject depends of course on the semantics of its synchronizer implementation.(AbstractQueuedSynchronizer.ConditionObject的行为依赖于AbstractQueuedSynchronizer实现类的语义)
    
- This class provides inspection, instrumentation, and monitoring methods for the internal queue, as well as similar methods for condition objects. These can be exported as desired into classes using an AbstractQueuedSynchronizer for their synchronization mechanics.
- (该类提供内部队列的检查、检测和监视方法，以及条件对象的类似方法。可以按需使用到这些类中-使用AbstractQueuedSynchronizer作为同步机制的类如ReentrantLock)  

- Serialization
    - Serialization of this class stores only the underlying atomic integer maintaining state, so deserialized objects have empty thread queues.
    - Typical subclasses requiring serializability will define a readObject method that restores this to a known initial state upon deserialization.(需要序列化的典型子类将定义一个readObject方法，该方法在反序列化时将其恢复到已知的初始状态。)
    
## Usage
- To use this class as the basis of a synchronizer, redefine the following methods, as applicable, by inspecting and/or modifying the synchronization state using getState(), setState(int) and/or compareAndSetState(int, int):
- (要使用这个类作为同步器的基础(如ReentrantLock的同步器的基础)，可以使用getState()、setState(int)和/或compareAndSetState(int, int)检查和/或修改同步状态，并根据需要重新定义以下方法:)
    - tryAcquire(int)
    - tryRelease(int)
    - tryAcquireShared(int)
    - tryReleaseShared(int)
    - isHeldExclusively()
    - Each of these methods by default throws UnsupportedOperationException.
    - **Implementations of these methods must be internally thread-safe, **
    - **and should in general be short and not block**
    - Defining these methods is the only supported means of using this class.(定义这些方法是使用该类的唯一受支持的方法)
    - All other methods are declared final because they cannot be independently varied.(所有其他(子类可以调用的)方法都被声明为final，因为它们不能独立地更改。)
    
- You may also find the inherited methods from AbstractOwnableSynchronizer useful to keep track of the thread owning an exclusive synchronizer. You are encouraged to use them -- this enables monitoring and diagnostic tools to assist users in determining which threads hold locks.

- The core of exclusive synchronization takes the form:
    ```
    Acquire:
         while (!tryAcquire(arg)) {
            enqueue thread if it is not already queued;
            possibly block current thread;
         }
    
     Release:
         if (tryRelease(arg))
            unblock the first queued thread;
    (Shared mode is similar but may involve cascading signals.)

    ```
    
- Fair
    - Even though this class is based on an internal FIFO queue, it does not automatically enforce FIFO acquisition policies.Because checks in acquire are invoked before enqueuing, a newly acquiring thread may barge ahead of others that are blocked and queued. However, you can, if desired, **define tryAcquire and/or tryAcquireShared to disable barging by internally invoking one or more of the inspection methods**, thereby providing a fair FIFO acquisition order. **In particular, most fair synchronizers can define tryAcquire to return false if hasQueuedPredecessors() (a method specifically designed to be used by fair synchronizers) returns true**.    
    - (通过hasQueuedPredecessors()返回true时tryAcquire返回false来实现公平锁 保证FIFO)
    
- Nofair **有多次的直接尝试获取, 但没有成功获取的线程依然会像Fair一样. 放入等待队列然后死循环等待获取，且和Fair复用同一份代码**
    - Throughput and scalability are generally highest for the default barging (also known as greedy, renouncement, and convoy-avoidance) strategy. (Nofair的吞吐量和可伸缩性更好)
    - While this is not guaranteed to be fair or starvation-free, earlier queued threads are allowed to recontend before later queued threads, and each recontention has an unbiased chance to succeed against incoming threads.
    - (虽然不能保证这是公平的或无饥饿的，但是允许较早的队列线程在较晚的队列线程之前重新争用，并且每次重新争用都有无偏倚的机会成功争用。)
    - ((Nofair同样会在AbstractQueuedSynchronizer#acquireQueued方法内死循环自旋))Also, while acquires do not "spin" in the usual sense, they may perform multiple invocations of tryAcquire interspersed with other computations before blocking.
    - (此外，虽然获取并不像通常意义上的“旋转”(其实是死循环自旋了)，但它们可能执行多次调用tryAcquire，并在阻塞之前穿插其他计算。)
    - This gives most of the benefits of spins when exclusive synchronization is only briefly held, without most of the liabilities when it isn't
    - (当独占同步只被短暂地持有时，这就提供了自旋的大部分好处(通过多次调用tryAcquire)，而当独占同步不被短暂持有时，则无需承担大部分责任)
    - If so desired, you can augment this by preceding calls to acquire methods with "fast-path" checks, possibly prechecking hasContended() and/or hasQueuedThreads() to only do so if the synchronizer is likely not to be contended.
    - hasContended()
    
- **公平锁和非公平锁都用到了FIFO等待队列. 只是公平锁通过hasQueuedPredecessors()严格保证了线程获取锁的FIFO顺序. 而非公平锁新的线程获取锁时不仅没有hasQueuedPredecessors()反而执行尝试获取锁从而可以barge(打破闯入)原有的FIFO顺序进而打破了FIFO顺序**

- This class provides an efficient and scalable basis for synchronization in part by specializing its range of use to synchronizers that can rely on int state, acquire, and release parameters, and an internal FIFO wait queue. When this does not suffice, you can build synchronizers from a lower level using atomic classes, your own custom Queue classes, and LockSupport blocking support.
- (这个类提供了一个高效的、可伸缩的同步基础，部分原因是通过将它的使用范围专门化到可以依赖于int状态、获取和释放参数以及内部FIFO等待队列的同步器. 当这还不够时，您可以使用原子类、您自己的自定义队列类和LockSupport阻塞支持从较低的级别构建同步器。) 

- a exclusive mode  
    - ReentrantLock
- a shared mode
    - CountDownLatch
    - Here is a latch class that is like a CountDownLatch except that it only requires a single signal to fire. Because a latch is non-exclusive, it uses the shared acquire and release methods.
        ```
         class BooleanLatch {
        
           private static class Sync extends AbstractQueuedSynchronizer {
             boolean isSignalled() { return getState() != 0; }
        
             protected int tryAcquireShared(int ignore) {
               return isSignalled() ? 1 : -1;
             }
        
             protected boolean tryReleaseShared(int ignore) {
               setState(1);
               return true;
             }
           }
        
           private final Sync sync = new Sync();
           public boolean isSignalled() { return sync.isSignalled(); }
           public void signal()         { sync.releaseShared(1); }
           public void await() throws InterruptedException {
             sync.acquireSharedInterruptibly(1);
           }
         }
    
        ```

    
    
    

