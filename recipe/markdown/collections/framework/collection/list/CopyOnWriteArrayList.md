# CopyOnWriteArrayList

- A thread-safe variant of ArrayList in which all mutative operations (add, set, and so on) are implemented by making a fresh copy of the underlying array.

- This is ordinarily too costly, but may be more efficient than alternatives **when traversal operations vastly outnumber mutations**, and **is useful when you cannot or don't want to synchronize traversals, yet need to preclude interference among concurrent threads**.(i.e., 读多于写且遍历时无需同步)

- The "snapshot" style iterator method uses a reference to the state of the array at the point that the iterator was created. The iterator will not reflect additions, removals, or changes to the list since the iterator was created.(i.e., iterator创建完之后就不会再反应list的修改)

- This array never changes during the lifetime of the iterator, so interference is impossible and the iterator is guaranteed not to throw ConcurrentModificationException. Element-changing operations on iterators themselves (remove, set, and add) are not supported. These methods throw UnsupportedOperationException

- All elements are permitted, including null.

- **taste**
    - [Branching Statements](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/branch.html) 
    ```
    /**
     * A version of remove(Object) using the strong hint that given
     * recent snapshot contains o at the given index.
     */
    private boolean remove(Object o, Object[] snapshot, int index) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] current = getArray();
            int len = current.length;
            if (snapshot != current) findIndex: {
                int prefix = Math.min(index, len);
                for (int i = 0; i < prefix; i++) {
                    if (current[i] != snapshot[i] && eq(o, current[i])) {
                        index = i;
                        break findIndex;
                    }
                }
                if (index >= len)
                    return false;
                if (current[index] == o)
                    break findIndex;
                index = indexOf(o, current, index, len);
                if (index < 0)
                    return false;
            }
            Object[] newElements = new Object[len - 1];
            System.arraycopy(current, 0, newElements, 0, index);
            System.arraycopy(current, index + 1,
                             newElements, index,
                             len - index - 1);
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }
    
    ```
    
    - label 
    ```
    block:
    {
        // some code
    
        if(condition) break block;
    
        // rest of code that won't be executed if condition is true
    }

    ```
 
- Hashtable-->ConcurrentHashMap，Vector-->CopyOnWriteArrayList， 加锁粒度变小
