# CompletionService

- A service that decouples the production of new asynchronous tasks from the consumption of the results of completed tasks

- Consumers take completed tasks and process their results in the order they complete 

- Typically, a CompletionService relies on a separate Executor to actually execute the tasks, in which case the CompletionService only manages an internal completion queue. The ExecutorCompletionService class provides an implementation of this approach.
  

