# Object


## 等待/通知机制
- 等待/通知机制，是指一个线程A调用了对象O的wait()方法进入等待状态，而另一个线程B调用了对象O的notify()/notifyAll()方法，线程A收到通知后退出等待队列，进入可运行状态，进而执行后续操作。
- 上诉两个线程通过对象O来完成交互，而对象上的wait()方法和notify()/notifyAll()方法的关系就如同开关信号一样，用来完成等待方和通知方之间的交互工作
- synchronized关键字可以将任何一个Object对象作为同步对象来看待，而Java为每个Object都实现了等待/通知（wait/notify）机制的相关方法，它们必须用在synchronized关键字同步的Object的临界区内。通过调用wait()方法可以使处于临界区内的线程进入等待状态，同时释放被同步对象的锁。而notify()方法可以唤醒一个因调用wait操作而处于阻塞状态中的线程，使其进入就绪状态。被重新唤醒的线程会视图重新获得临界区的控制权也就是锁，并继续执行wait方法之后的代码。如果发出notify操作时没有处于阻塞状态中的线程，那么该命令会被忽略。
  
- 当方法wait()被执行后，锁自动被释放，但执行完notify()方法后，锁不会自动释放。必须执行完notify()方法所在的synchronized代码块后才释放
- WAITING TIMED_WAITING 状态不释放锁
- RUNNABLE 状态可能在等待cpu时间片
- yield() 让出cpu的使用权，使调度器重新调度: A hint to the scheduler that the current thread is willing to yield its current use of a processor. The scheduler is free to ignore this hint.
- 当线程呈wait状态时，对线程对象调用interrupt方法会出现InterrupedException异常 Object#wait join sleep 


## wait
- wait (类似 Condition#await)
    - wait()  wait(0, 0)  wait(0) 的完全一样，仅notify通知或中断
    - 使调用该方法的线程释放共享资源锁，然后从运行状态退出，进入等待队列，直到被再次唤醒
    
    - 概述: 
        - 当前线程拥有该Object的监视器锁的前提调用该方法，然后释放锁，await返回前会重新获取锁
        - This method should only be called by a thread that is the owner of this object's monitor. The current thread must own this object's monitor.
        - Causes the current thread to wait 
        - until either another thread invokes the notify() method or the notifyAll() method for this object, 
        - or a specified amount of time has elapsed.
        
    - 详细:
        - This method causes the current thread (call it T) to place itself in the wait set for this object and then to relinquish any and all synchronization claims on this object. 
        - (该方法导致当前线程(称为T)将自己放入该对象的等待集中，然后放弃该对象上的任何和所有同步声明。)
        
        - (unlocks only this object. any other objects on which the current thread may be synchronized remain locked while the thread waits)
        
        - Thread T becomes disabled for thread scheduling purposes and lies dormant until one of four things happens:
            - Some other thread invokes the notify method for this object and thread T happens to be arbitrarily chosen as the thread to be awakened.
            - Some other thread invokes the notifyAll method for this object.
            - Some other thread interrupts(Thread#interrupt()) thread T. (中断异常抛出 after the lock status of this object has been restored)
            - The specified amount of real time has elapsed, more or less. If timeout is zero, however, then real time is not taken into consideration and the thread simply waits until notified.
            - (指定的实时时间或多或少已经过了。但是，如果超时为零，则不考虑超时，线程只是等待通知。)
    
        - The thread T is then removed from the wait set for this object and re-enabled for thread scheduling.
        - It then competes in the usual manner with other threads for the right to synchronize on the object; 
        - once it has gained control of the object, all its synchronization claims on the object are restored to the status quo ante - that is, to the situation as of the time that the wait method was invoked.
        - Thread T then returns from the invocation of the wait method. 
        - Thus, on return from the wait method, the synchronization state of the object and of thread T is exactly as it was when the wait method was invoked.
        
    - spurious wakeup
        - A thread can also wake up without being notified, interrupted, or timing out, a so-called spurious wakeup.线程也可以在不被通知、中断或超时的情况下唤醒，这就是所谓的伪唤醒。(这个特性类似 LockSupport.park)
        - While this will rarely occur in practice, applications must guard against it by testing for the condition that should have caused the thread to be awakened, and continuing to wait if the condition is not satisfied.应用程序必须通过测试导致线程被唤醒的条件来防范这种情况，
        - In other words, waits should always occur in loops, like this one:
            ```
                 synchronized (obj) {
                     while (<condition does not hold>)
                         obj.wait(timeout);
                     ... // Perform action appropriate to condition
                 }
            ```
- If the current thread is interrupted by any thread before or while it is waiting, then an InterruptedException is thrown. This exception is not thrown until the lock status of this object has been restored as described above.
    
- public final void wait(long timeout, int nanos) throws InterruptedException
    - In all other respects, this method does the same thing as the method wait(long) of one argument. In particular, wait(0, 0) means the same thing as wait(0).
    - 在所有其他方面，此方法与方法wait(long)对一个参数执行相同的操作。特别是，wait(0,0)与wait(0)含义相同。
      
    - The current thread must own this object's monitor. The thread releases ownership of this monitor and waits until either of the following two conditions has occurred:
        - Another thread notifies threads waiting on this object's monitor to wake up either through a call to the notify method or the notifyAll method.
        - The timeout period, specified by timeout milliseconds plus nanos nanoseconds arguments, has elapsed.
    - The thread then waits until it can re-obtain ownership of the monitor and resumes execution.
    - As in the one argument version, interrupts and spurious wakeups are possible.

- public final void wait() throws InterruptedException
    - this method behaves exactly as if it simply performs the call wait(0).
    - Causes the current thread to wait until another thread invokes the notify() method or the notifyAll() method for this object. In other words, this method behaves exactly as if it simply performs the call wait(0).
    - The current thread must own this object's monitor. The thread releases ownership of this monitor and waits until another thread notifies threads waiting on this object's monitor to wake up either through a call to the notify method or the notifyAll method. The thread then waits until it can re-obtain ownership of the monitor and resumes execution.
    - As in the one argument version, interrupts and spurious wakeups are possible

## notify

- This method should only be called by a thread that is the owner of this object's monitor.
- A thread becomes the owner of the object's monitor in one of three ways:
    - By executing a synchronized instance method of that object.  该对象的synchronized实例方法
    - By executing the body of a synchronized statement that synchronizes on the object. 同步该对象synchronized statement
    - For objects of type Class, by executing a synchronized static method of that class. 该对象的synchronized类静态方法

- Only one thread at a time can own an object's monitor.


- notify()
    - 随机唤醒等待队列中等待同一共享资源的 “一个线程”，并使该线程退出等待队列，进入可运行状态，也就是notify()方法仅通知“一个线程”
    - If any threads are waiting on this object, one of them is chosen to be awakened. The choice is arbitrary and occurs at the discretion of the implementation
    - A thread waits on an object's monitor by calling one of the wait methods.
    
    - The awakened thread will not be able to proceed until the current thread relinquishes the lock on this object. 
    - (被唤醒的线程将无法继续，直到当前线程释放该对象上的锁。类似Condition#signal)
    - The awakened thread will compete in the usual manner with any other threads that might be actively competing to synchronize on this object;
    - (被唤醒的线程将以通常的方式与任何其他线程竞争，这些线程可能正在积极地竞争同步该对象;)
    
    
- notifyAll()
    - Wakes up all threads that are waiting on this object's monitor.
    - 使所有正在等待队列中等待同一共享资源的 “全部线程” 退出等待队列，进入可运行状态。此时，优先级最高的那个线程最先执行，但也有可能是随机执行，这取决于JVM虚拟机的实现
      