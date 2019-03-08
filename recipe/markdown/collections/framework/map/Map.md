# Map

- An object that maps keys to values. A map cannot contain duplicate keys; each key can map to at most one value.

- This interface takes the place of the Dictionary class, which was a totally abstract class rather than an interface.
  
- The Map interface provides three collection views, which allow a map's contents to be viewed as a set of keys, collection of values, or set of key-value mappings.

- The order of a map is defined as the order in which the iterators on the map's collection views return their elements. Some map implementations, like the TreeMap class, make specific guarantees as to their order; others, like the HashMap class, do not.

- **Note**
    - great care must be exercised if mutable objects are used as map keys. The behavior of a map is not specified if the value of an object is changed in a manner that affects equals comparisons while the object is a key in the map.
        ```
         ((k = p.key) == key || (key != null && key.equals(k))))
        ```
    - A special case of this prohibition is that it is not permissible for a map to contain itself as a key. 
    - While it is permissible for a map to contain itself as a value, extreme caution is advised: the equals and hashCode methods are no longer well defined on such a map.

- a constructor with a single argument of type Map allows the user to copy any map, producing an equivalent map of the desired class.(新旧map key value都引用相同)


- Many methods in Collections Framework interfaces are defined in terms of the equals method.

- This specification should not be construed to imply that invoking Map.containsKey with a non-null argument key will cause key.equals(k) to be invoked for any key k.

- Implementations are free to implement optimizations whereby the equals invocation is avoided, for example, by first comparing the hash codes of the two keys. (The Object.hashCode() specification guarantees that two objects with unequal hash codes cannot be equal.) 

- More generally, implementations of the various Collections Framework interfaces are free to take advantage of the specified behavior of underlying Object methods wherever the implementor deems it appropriate.


- *complex*
    - Some map operations which perform recursive traversal of the map may fail with an exception for self-referential instances where the map directly or indirectly contains itself. This includes the clone(), equals(), hashCode() and toString() methods. Implementations may optionally handle the self-referential scenario, however most current implementations do not do so.
      
