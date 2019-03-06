# ArrayList

- Resizable-array implementation of the List interface. (List接口的可调整大小的数组实现)
- permits null
- this class provides methods to manipulate the size of the array that is used internally to store the list, In addition to implementing the List interface. 

- **(This class is roughly equivalent to Vector, except that it is unsynchronized.)**

- The add operation runs in amortized constant time, that is, adding n elements requires O(n) time(i.e., 在指定位置添加元素时间复杂度是O(n)). The constant factor is low compared to that for the LinkedList implementation.

- The capacity of an ArrayList instance is the size of the array used to store the elements in the list, As elements are added to an ArrayList, its capacity grows automatically.

- increase the capacity of an ArrayList instance before adding a large number of elements using the ensureCapacity operation. This may reduce the amount of incremental reallocation(i.e., 这可能会减少增量重新分配的次数).

- (A structural modification is any operation that **adds** or **deletes** one or more elements, or explicitly **resizes the backing array**; merely setting the value of an element is not a structural modification.)

- Synchronization is typically accomplished by synchronizing on some object that naturally encapsulates the list. If no such object exists, the list should be "wrapped" using the Collections.synchronizedList method. This is best done at creation time, to prevent accidental unsynchronized access to the list:

- The iterators returned by this class's iterator and listIterator methods are fail-fast: if the list is structurally modified at any time after the iterator is created, in any way except through the iterator's own remove or add methods, the iterator will throw a ConcurrentModificationException.

