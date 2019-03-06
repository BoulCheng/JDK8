# AbstractList

- This class provides a skeletal implementation of the List interface to minimize the effort required to implement this interface backed by a "random access" data store (such as an array)

- For sequential access data (such as a linked list), AbstractSequentialList should be used in preference to this class.(i.e., 有序访问优先使用 AbstractSequentialList)

- To implement an unmodifiable list, the programmer needs only to extend this class and provide implementations for the **get(int) and size()** methods.
  
- To implement a modifiable list, the programmer must additionally override the set(int, E) method (which otherwise throws an UnsupportedOperationException). If the list is variable-size the programmer must additionally override the add(int, E) and remove(int) methods.
  
- The programmer should generally provide a void (no argument) and collection constructor, as per the recommendation in the Collection interface specification.
  
- the iterator and list iterator are implemented by this class, on top of the "random access" methods: get(int), set(int, E), add(int, E) and remove(int).
