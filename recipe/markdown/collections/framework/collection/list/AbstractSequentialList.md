# AbstractSequentialList

- This class provides a skeletal implementation of the List interface to minimize the effort required to implement this interface **backed by a "sequential access" data store (such as a linked list)**.

- For random access data (such as an array), AbstractList should be used in preference to this class.

- To implement a list the programmer needs only to extend this class and provide implementations for the listIterator and size methods. For an unmodifiable list, the programmer need only implement the list iterator's hasNext, next, hasPrevious, previous and index methods.
  
- For a modifiable list the programmer should additionally implement the list iterator's set method. For a variable-size list the programmer should additionally implement the list iterator's remove and add methods.
  
