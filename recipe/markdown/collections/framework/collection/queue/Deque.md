# Deque

- A linear collection that supports element insertion and removal at both ends. The name deque is short for "double ended queue" and is usually pronounced "deck". 

- Most Deque implementations place no fixed limits on the number of elements they may contain, but this interface supports capacity-restricted deques as well as those with no fixed size limit.

- This interface defines methods to access the elements at both ends of the deque. Methods are provided to insert, remove, and examine the element. Each of these methods exists in two forms: one throws an exception if the operation fails, the other returns a special value (either null or false, depending on the operation). The latter form of the insert operation is designed specifically for use with capacity-restricted Deque implementations; in most implementations, insert operations cannot fail. The twelve methods described above are summarized in the following table: 

- This interface extends the Queue interface. When a deque is used as a queue, FIFO (First-In-First-Out) behavior results. Elements are added at the end of the deque and removed from the beginning. The methods inherited from the Queue interface are precisely equivalent to Deque methods as indicated in the following table:
  
- Deques can also be used as LIFO (Last-In-First-Out) stacks. This interface should be used in preference to the legacy Stack class. When a deque is used as a stack, elements are pushed and popped from the beginning of the deque. Stack methods are precisely equivalent to Deque methods as indicated in the table below:
  
- Note that the peek method works equally well when a deque is used as a queue or a stack; in either case, elements are drawn from the beginning of the deque.
  
- This interface provides two methods to remove interior elements, removeFirstOccurrence and removeLastOccurrence.
  
- Unlike the List interface, this interface does not provide support for indexed access to elements.
  
- While Deque implementations are not strictly required to prohibit the insertion of null elements, they are strongly encouraged to do so. Users of any Deque implementations that do allow null elements are strongly encouraged not to take advantage of the ability to insert nulls. This is so because null is used as a special return value by various methods to indicated that the deque is empty.

- Deque implementations generally do not define element-based versions of the equals and hashCode methods, but instead inherit the identity-based versions from class Object.
  
