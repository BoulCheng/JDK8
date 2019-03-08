# TreeSet

- A NavigableSet implementation based on a TreeMap

- This implementation provides guaranteed log(n) time cost for the basic operations (add, remove and contains).
  
- not synchronized
    ```
    SortedSet s = Collections.synchronizedSortedSet(new TreeSet(...));
    ```