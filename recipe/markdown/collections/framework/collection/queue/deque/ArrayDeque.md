# ArrayDeque

- Resizable-array implementation of the Deque interface.

- Array deques have no capacity restrictions; they grow as necessary to support usage.(doubleCapacity())

- Null elements are prohibited.

- This class is likely to be faster than Stack when used as a stack

- and faster than LinkedList when used as a queue.


- Most ArrayDeque operations run in amortized constant time. Exceptions include remove, removeFirstOccurrence, removeLastOccurrence, contains, iterator.remove(), and the bulk operations, all of which run in linear time.
  
    ```
        public E pollFirst() {
            int h = head;
            @SuppressWarnings("unchecked")
            E result = (E) elements[h];
            // Element is null if deque empty
            if (result == null)
                return null;
            elements[h] = null;     // Must null out slot
            head = (h + 1) & (elements.length - 1);
            return result;
        }
    
        public E pollLast() {
            int t = (tail - 1) & (elements.length - 1);
            @SuppressWarnings("unchecked")
            E result = (E) elements[t];
            if (result == null)
                return null;
            elements[t] = null;
            tail = t;
            return result;
        }
    ```
    
- 