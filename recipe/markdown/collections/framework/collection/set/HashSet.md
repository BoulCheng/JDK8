# HashSet

- This class implements the Set interface, backed by a hash table (actually a HashMap instance).

- It makes no guarantees as to the iteration order of the set; in particular, it does not guarantee that the order will remain constant over time.

- This class permits the null element.

- This class offers constant time performance for the basic operations (add, remove, contains and size), assuming the hash function disperses the elements properly among the buckets.

- Iterating over this set requires time proportional to the sum of the HashSet instance's size (the number of elements) plus the "capacity" of the backing HashMap instance (the number of buckets). 

- Thus, it's very important not to set the initial capacity too high (or the load factor too low) if iteration performance is important.
