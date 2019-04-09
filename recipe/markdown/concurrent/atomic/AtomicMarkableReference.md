# AtomicMarkableReference
- AtomicStampedReference 可以给引用加上版本号，追踪引用的整个变化过程

- AtomicMarkableReference对于那些不关心引用变化过程，只关心引用变量是否变化过的应用会更加友好。

- AtomicMarkableReference与AtomicStampedReference的唯一区别就是不再用int标识引用，而是使用boolean变量——表示引用变量是否被更改过。