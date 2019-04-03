# ScheduledExecutorService

- An ExecutorService that can schedule commands to run after a given delay, or to execute periodically.
- Zero and negative delays (but not periods) are also allowed in schedule methods, and are treated as requests for immediate execution.
- Commands submitted using the Executor.execute(Runnable) and ExecutorService submit methods are scheduled with a requested delay of zero.

- All schedule methods accept relative delays and periods as arguments, not absolute times or dates.
- For example, to schedule at a certain future date, you can use: schedule(task, date.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS). 

- Beware however that expiration of a relative delay need not coincide with the current Date at which the task is enabled due to network time synchronization protocols, clock drift, or other factors.
- (但是要注意，由于网络时间同步协议、时钟漂移或其他因素，相对延迟的过期不一定与当前启用任务的日期一致。)  


- The Executors class provides convenient factory methods for the ScheduledExecutorService implementations provided in this package.
  


- ScheduledFuture<?>	scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
    - Creates and executes a periodic action that becomes enabled first after the given initial delay, and subsequently with the given period; that is executions will commence after initialDelay then initialDelay+period, then initialDelay + 2 * period, and so on.
    - If any execution of the task encounters an exception, subsequent executions are suppressed. Otherwise, the task will only terminate via cancellation or termination of the executor. 
    - If any execution of this task takes longer than its period, then subsequent executions may start late, but will not concurrently execute.

- ScheduledFuture<?>	scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)
    - Creates and executes a periodic action that becomes enabled first after the given initial delay, and subsequently with the given delay between the termination of one execution and the commencement of the next. (然后在一次执行的终止和下一次执行的开始之间使用给定的延迟。)
    - If any execution of the task encounters an exception, subsequent executions are suppressed. Otherwise, the task will only terminate via cancellation or termination of the executor.