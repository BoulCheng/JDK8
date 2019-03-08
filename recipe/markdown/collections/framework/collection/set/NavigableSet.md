# NavigableSet

- 这个接口是 NavigableMap 的Set - analogue。

- A SortedSet extended with navigation methods reporting closest matches for given search targets.

- The return values of navigation methods may be ambiguous in implementations that permit null elements. 
- However, even in this case the result can be disambiguated by checking contains(null). 
- To avoid such issues, implementations of this interface are encouraged to not permit insertion of null elements. (Note that sorted sets of Comparable elements intrinsically do not permit null.)
  

