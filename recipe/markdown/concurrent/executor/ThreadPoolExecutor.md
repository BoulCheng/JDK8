# ThreadPoolExecutor

- normally configured using Executors factory methods.

- Thread pools address two different problems: 
    - they usually provide improved performance when executing large numbers of asynchronous tasks, due to reduced per-task invocation overhead,
    - and they provide a means of bounding and managing the resources, including threads, consumed when executing a collection of tasks.
    -  Each ThreadPoolExecutor also maintains some basic statistics, such as the number of completed tasks.
    
- However, programmers are urged to use the more convenient Executors factory methods that preconfigure settings for the most common usage scenarios. 
    - Executors.newCachedThreadPool() (unbounded thread pool, with automatic thread reclamation), 
    - Executors.newFixedThreadPool(int) (fixed size thread pool) 
    - Executors.newSingleThreadExecutor() (single background thread), 
    
- Otherwise, use the following guide when manually configuring and tuning this class:
    - Core and maximum pool sizes
        - getPoolSize
        - setCorePoolSize(int) and setMaximumPoolSize(int).
        - task、线程、队列之间的关系
    - On-demand construction
        - You probably want to prestart threads if you construct the pool with a non-empty queue.
        - By default, even core threads are initially created and started only when new tasks arrive, but this can be overridden dynamically using method prestartCoreThread() or prestartAllCoreThreads().
    - Creating new threads
        - with the same NORM_PRIORITY priority and non-daemon status. 
    - Keep-alive times
        - If the pool currently has **more than corePoolSize threads**, **excess threads** will be terminated if they have been idle for more than the keepAliveTime (see getKeepAliveTime(TimeUnit))
        - can also be changed dynamically using method setKeepAliveTime(long, TimeUnit)
        - the keep-alive policy applies only when there are more than corePoolSize threads.
        - method allowCoreThreadTimeOut(boolean) can be used to apply this time-out policy to core threads as well
    - Queuing
        - Any BlockingQueue may be used to transfer and hold submitted tasks.
        - The use of this queue interacts with pool sizing:
            - If fewer than corePoolSize threads are running, the Executor always prefers adding a new thread rather than queuing.
            - If corePoolSize or more threads are running, the Executor always prefers queuing a request rather than adding a new thread.
            - If a request cannot be queued, a new thread is created unless this would exceed maximumPoolSize, in which case, the task will be rejected.
        - There are three general strategies for queuing:
            - Direct handoffs.(SynchronousQueue) :Here, an attempt to queue a task will fail if no threads are immediately available to run it, so a new thread will be constructed.
            - Unbounded queues. (for example a LinkedBlockingQueue without a predefined capacity)
            - Bounded queues.(for example, an ArrayBlockingQueue)
                - ueue sizes and maximum pool sizes may be traded off for each other:
                - Using large queues and small pools minimizes CPU usage, OS resources, and context-switching overhead, but can lead to artificially low throughput.
                - Use of small queues generally requires larger pool sizes, which keeps CPUs busier but may encounter unacceptable scheduling overhead, which also decreases throughput
            
    - Rejected tasks. Four predefined handler policies are provided: (It is possible to define and use other kinds of RejectedExecutionHandler classes)
        - AbortPolicy, throws a runtime RejectedExecutionException
        - CallerRunsPolicy, the thread that invokes execute itself runs the task. This provides a simple feedback control mechanism that will slow down the rate that new tasks are submitted.
        - DiscardPolicy, a task that cannot be executed is simply dropped.
        - DiscardOldestPolicy, the task at the head of the work queue is dropped, and then execution is retried (which can fail again, causing this to be repeated.)
        
    - Hook methods
        - this class provides protected overridable beforeExecute(Thread, Runnable) and afterExecute(Runnable, Throwable) methods that are called before and after execution of each task.
        - method terminated() can be overridden to perform any special processing that needs to be done once the Executor has fully terminated. 
            - TIDYING -> TERMINATED When the terminated() hook method has completed
            - Threads waiting in awaitTermination() will return when the state reaches TERMINATED.
            
    - Finalization
        - If you would like to ensure that unreferenced pools are reclaimed even if users forget to call shutdown()
        - must arrange that unused threads eventually die, by setting appropriate keep-alive times, using a lower bound of zero core threads and/or setting allowCoreThreadTimeOut(boolean).