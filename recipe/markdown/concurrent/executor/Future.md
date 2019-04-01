# Future

- A Future represents the result of an asynchronous computation.Future表示异步计算的结果。
- The result can only be retrieved using method get when the computation has completed, blocking if necessary until it is ready. 
- (只有当计算完成时，才可以使用get方法检索结果，如果需要，可以进行阻塞，直到准备好为止。)

- Once a computation has completed, the computation cannot be cancelled. 

- If you would like to use a Future for the sake of cancellability but not provide a usable result, you can declare types of the form Future<?> and return null as a result of the underlying task.
- (如果为了可取消而不提供可用的结果而希望使用Future，则可以声明形式Future<?>并返回null作为底层任务的结果。)


- Method
    - boolean cancel(boolean mayInterruptIfRunning)
        - If the task has already started, then the mayInterruptIfRunning parameter determines whether the thread executing this task should be interrupted in an attempt to stop the task.
        - 如果任务已经启动，那么mayInterruptIfRunning参数决定是否应该中断执行该任务的线程，以尝试停止该任务。
        
        - If successful, and this task has not started when cancel is called, this task should never run.
    - boolean isDone()
        - Returns true if this task completed. Completion may be due to normal termination, an exception, or cancellation -- in all of these cases, this method will return true.

    - boolean isCancelled()
        - Returns true if this task was cancelled before it completed normally.

    - V	get(long timeout, TimeUnit unit)
        - Waits if necessary for at most the given time for the computation to complete, and then retrieves its result, if available.

        