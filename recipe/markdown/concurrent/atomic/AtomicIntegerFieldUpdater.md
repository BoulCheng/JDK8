# AtomicIntegerFieldUpdater
- {@code volatile int} fields 需要volatile修饰
- Because this class cannot ensure that all uses of the field are appropriate for purposes of atomic access, it can guarantee atomicity only with respect to other invocations of compareAndSet and set on the same updater.
- Note that the guarantees of the compareAndSet method in this class are weaker than in other atomic classes. 

- 在同一个AtomicIntegerFieldUpdater子类中更新才能保证原子性