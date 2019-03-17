# AbstractOwnableSynchronizer

- (创建独占锁和同步器的抽象类)
- A synchronizer that may be exclusively owned by a thread.(可由线程独占的同步器。)

- This class provides a basis for creating locks and related synchronizers that may entail a notion of ownership.
- (该类为创建可能需要所有权概念的锁和相关同步器提供了基础。)

- The AbstractOwnableSynchronizer class itself does not manage or use this information. However, subclasses and tools may use appropriately maintained values to help control and monitor access and provide diagnostics.
- (AbstractOwnableSynchronizer类本身并不管理或使用这些信息。但是，子类和工具可以使用适当维护的值来帮助控制和监视访问并提供诊断。)

