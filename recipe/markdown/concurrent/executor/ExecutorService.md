# ExecutorService

- extends Executor

- An Executor that provides methods to manage termination and methods that can produce a Future for tracking progress of one or more asynchronous tasks.

- About termination
    - An ExecutorService can be shut down, which will cause it to reject new tasks.
    - Two different methods are provided for shutting down an ExecutorService.  
        - shutdown() 拒绝提交 等待已提交的任务运行完
            - the shutdown() method will allow previously submitted tasks to execute before terminating,
        - shutdownNow() 拒绝提交 阻止已提交的任务启动运行 直接停止正在运行的任务
            - while the shutdownNow() method prevents waiting tasks from starting and attempts to stop currently executing tasks. 
            
    - Upon termination, an executor has no tasks actively executing, no tasks awaiting execution, and no new tasks can be submitted.
    - An unused ExecutorService should be shut down to allow reclamation of its resources.
    
- About future submit方法(底层调用了execute方法)
    - Method submit extends base method Executor.execute(Runnable) by creating and returning a Future that can be used to cancel execution and/or wait for completion.
    - Methods invokeAny and invokeAll perform the most commonly useful forms of bulk execution, executing a collection of tasks and then waiting for at least one, or all, to complete.
    - (Class ExecutorCompletionService can be used to write customized variants of these methods.)
      
- The Executors class provides factory methods for the executor services provided in this package.
  

- usage
    - The following method shuts down an ExecutorService in two phases, first by calling shutdown to reject incoming tasks, and then calling shutdownNow, if necessary, to cancel any lingering tasks:
        ```
        void shutdownAndAwaitTermination(ExecutorService pool) {
           pool.shutdown(); // Disable new tasks from being submitted
           try {
             // Wait a while for existing tasks to terminate
             if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
               pool.shutdownNow(); // Cancel currently executing tasks
               // Wait a while for tasks to respond to being cancelled
               if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                   System.err.println("Pool did not terminate");
             }
           } catch (InterruptedException ie) {
             // (Re-)Cancel if current thread also interrupted
             pool.shutdownNow();
             // Preserve interrupt status
             Thread.currentThread().interrupt();
           }
         }
        ```