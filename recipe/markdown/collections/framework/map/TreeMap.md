# TreeMap

- A Red-Black tree based NavigableMap implementation.

- The map is sorted according to the natural ordering of its keys, or by a Comparator provided at map creation time, depending on which constructor is used.

- This implementation provides guaranteed log(n) time cost for the containsKey, get, put and remove operations.

- Note that the ordering must be consistent with equals if this sorted map is to correctly implement the Map interface.

- This is so because the Map interface is defined in terms of the equals operation, but a sorted map performs all key comparisons using its compareTo (or compare) method

- synchronized: This is best done at creation time, to prevent accidental unsynchronized access to the map: SortedMap m = Collections.synchronizedSortedMap(new TreeMap(...));


- All Map.Entry pairs returned by methods in this class and its views represent snapshots of mappings at the time they were produced. They do not support the Entry.setValue method. (Note however that it is possible to change mappings in the associated map using put.)
  
