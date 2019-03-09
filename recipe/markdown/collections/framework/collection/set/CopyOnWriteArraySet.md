# CopyOnWriteArraySet

- A Set that uses an internal CopyOnWriteArrayList for all of its operations. 

- CopyOnWriteArrayList内部有针对非重复元素的一些操作

- Thus, it shares the same basic properties:
    - (读远远大于写)It is best suited for applications in which set sizes generally stay small, read-only operations vastly outnumber mutative operations, and you need to prevent interference among threads during traversal.
    - (线程安全)It is thread-safe.
    - (写耗费资源很大)Mutative operations (add, set, remove, etc.) are expensive since they usually entail copying the entire underlying array.
    - (遍历不可remove)Iterators do not support the mutative remove operation.
    - (遍历非常快)Traversal via iterators is fast and cannot encounter interference from other threads. Iterators rely on unchanging snapshots of the array at the time the iterators were constructed.