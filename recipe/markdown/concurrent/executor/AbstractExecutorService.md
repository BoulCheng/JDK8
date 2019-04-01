# AbstractExecutorService

- Provides default implementations of ExecutorService execution methods.

- This class implements the submit, invokeAny and invokeAll methods using a RunnableFuture returned by newTaskFor, which defaults to the FutureTask class provided in this package. 

- Subclasses may override the newTaskFor methods to return RunnableFuture implementations other than FutureTask.