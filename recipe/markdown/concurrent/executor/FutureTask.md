# FutureTask

- A cancellable asynchronous computation.

- This class provides a base implementation of Future

- A FutureTask can be used to wrap a Callable or Runnable object. Because FutureTask implements Runnable, a FutureTask can be submitted to an Executor for execution.

- the get methods will block if the computation has not yet completed. 

- Once the computation has completed, the computation cannot be restarted or cancelled (unless the computation is invoked using runAndReset()).

  
