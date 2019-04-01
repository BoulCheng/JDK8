# Executor

- An object that executes submitted Runnable tasks.

- This interface provides a way of decoupling task submission from the mechanics of how each task will be run, including details of thread use, scheduling, etc.
- (该接口提供了一种方法，可以将任务提交与每个任务的运行机制(包括线程使用、调度等细节)分离开来。)

- An Executor is normally used instead of explicitly creating threads. 
- (通常使用执行器而不是显式地创建线程。)


- some usage
    - However, the Executor interface does not strictly require that execution be asynchronous. In the simplest case, an executor can run the submitted task immediately in the caller's thread:

    - More typically, tasks are executed in some thread other than the caller's thread. The executor below spawns a new thread for each task.

    - Many Executor implementations impose some sort of limitation on how and when tasks are scheduled. The executor below serializes the submission of tasks to a second executor, illustrating a composite executor.

- The Executor implementations provided in this package implement ExecutorService, which is a more extensive interface.

- The ThreadPoolExecutor class provides an extensible thread pool implementation. (ThreadPoolExecutor类提供了一个可扩展的线程池实现。)

- The Executors class provides convenient factory methods for these Executors.



- execute(Runnable command)
    - Executes the given command at some time in the future. 
    - The command may execute in a new thread, in a pooled thread, or in the calling thread, at the discretion of the {@code Executor} implementation.