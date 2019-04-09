# AtomicIntegerArray
- 以原子的方式，操作数组中的元素
- 数组原子类与对应的普通原子类相比，只是多了通过索引找到内存中数组元素相对于数组变量的地址偏移量的操作而已。

- int base = unsafe.arrayBaseOffset(int[].class); Unsafe类的arraBaseOffset方法：返回指定类型数组的第一个元素地址相对于数组起始地址的偏移值

- int scale = unsafe.arrayIndexScale(int[].class); Unsafe类的arrayIndexScale方法：返回指定类型数组的元素所占用的字节数。比如int[]数组中的每个int元素占用4个字节，就返回4。
- 那么，通过base + i * sacle 其实就可以知道 索引i的元素在数组中的内存起始地址。