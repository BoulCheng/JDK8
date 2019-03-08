# AbstractMap

- To implement an unmodifiable map
    - extend this class and provide an implementation for the entrySet method, which returns a set-view of the map's mappings. Typically, the returned set will, in turn, be implemented atop AbstractSet. This set should not support the add or remove methods, and its iterator should not support the remove method.
    - (HashMap entrySet()实现)
- To implement a modifiable map
    - additionally override this class's put method. and the iterator returned by entrySet().iterator() must additionally implement its remove method.
    
- SimpleEntry 
- SimpleImmutableEntry