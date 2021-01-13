# LockSupport

- core
    - 用于创建锁和其他同步类的基本线程阻塞原语
    - 方法park和unpark提供了阻塞和解除阻塞线程的有效方法
    - 许可证不会累积，最多只有一个。不同于Semaphores
    - 调用park方法，如果许可可获得则立即返回并消耗这个许可，如果许可不可获得则阻塞；park will return if the caller's thread was interrupted, and timeout versions are supported.
    - 调用unpark方法归还许可，是许可可获得，但许可最多只有一个多次调用也只会有一个许可
    - The park method may also return at any other time, for "no reason", so in general must be invoked within a loop that rechecks conditions upon return.In this sense park serves as an optimization of a "busy wait" that does not waste as much time spinning, but must be paired with an unpark to be effective.

- Basic thread blocking primitives for creating locks and other synchronization classes.
- (用于创建锁和其他同步类的基本线程阻塞原语。)

- This class associates, with each thread that uses it, a permit (in the sense of the Semaphore class)
- (这个类与使用它的每个线程关联一个许可证(在信号量类的意义上))
    - A call to park will return immediately if the permit is available, consuming it in the process; otherwise it may block.
    - (如果许可可获得则调用park立即返回且使用这个许可，否则可能阻塞)
    
    - A call to unpark makes the permit available, if it was not already available. (Unlike with Semaphores though, permits do not accumulate. There is at most one.)
    - (调用unpark使许可可获得，如果许可不是可获得的。与信号量不同的是，许可证不会累积。最多只有一个。)

- Methods park and unpark provide efficient means of blocking and unblocking threads
    
- park
    - Additionally, park will return if the caller's thread was interrupted, and timeout versions are supported.
    
    - The park method may also return at any other time, for "no reason", so in general must be invoked within a loop that rechecks conditions upon return.
    - (park方法也可以在任何其他时间返回，原因是“没有原因”，所以通常必须在循环中调用park方法，以便在返回时重新检查条件。)
    
    - In this sense park serves as an optimization of a "busy wait" that does not waste as much time spinning, but must be paired with an unpark to be effective
    - (从这个意义上讲，park是“繁忙等待”的优化，它不会浪费太多的时间旋转，但必须与unpark相匹配才能有效)
    
    - The three forms of park each also support a blocker object parameter. This object is recorded while the thread is blocked to permit monitoring and diagnostic tools to identify the reasons that threads are blocked. The use of these forms rather than the original forms without this parameter is strongly encouraged. The normal argument to supply as a blocker within a lock implementation is this.
    - (在线程被阻塞时记录此对象，以便允许监视和诊断工具识别线程被阻塞的原因。这种方式被强烈推荐)
    
    - usage
        - These methods are designed to be used as tools for creating higher-level synchronization utilities, and are not in themselves useful for most concurrency control applications.
        - The park method is designed for use only in constructions of the form: (park方法设计为仅用于以下形式:
            ```
             while (!canProceed()) { ... LockSupport.park(this); }
        
            ```
        - where neither canProceed nor any other actions prior to the call to park entail locking or blocking.(在调用park之前既不能继续也不能执行任何其他操作的情况下，需要锁定或阻塞。)
        - Because only one permit is associated with each thread, any intermediary uses of park could interfere with its intended effects.(因为每个线程只有一个许可证，所以park的任何中间用途都可能干扰其预期的效果。)
    