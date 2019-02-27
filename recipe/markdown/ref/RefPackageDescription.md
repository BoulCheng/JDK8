#Package java.lang.ref Description

- Provides reference-object classes, which support a limited degree of interaction with the garbage collector

- A reference object encapsulates a reference to some other object so that the reference itself may be examined and manipulated like any other object. 

- Three types of reference objects are provided, each weaker than the last: soft, weak, and phantom

- Each type corresponds to a different level of reachability, as defined below
    - Soft references are for implementing memory-sensitive caches
    - weak references are for implementing canonicalizing mappings that do not prevent their keys (or values) from being reclaimed
    - phantom references are for scheduling pre-mortem cleanup actions in a more flexible way than is possible with the Java finalization mechanism.
    
- An instance of one of these subclasses encapsulates a single reference to a particular object, called the referent

- Aside from the clearing operation reference objects are otherwise immutable, so no set operation is provided.

- Reachability
    - strongly reachable
    - softly reachable
    - weakly reachable. When the weak references to a weakly-reachable object are cleared, the object becomes eligible for finalization.
    - phantom reachable 
    
- **an object is unreachable, and therefore eligible for reclamation, when it is not reachable in any of the above ways.**
 
 
- *other information*
    - 强引用. 被强引用指向的对象，绝对不会被垃圾收集器回收。Integer prime = 1;，这个语句中prime对象就有一个强引用。
    - 软引用. 被SoftReference指向的对象可能会被垃圾收集器回收，但是只有在JVM内存不够的情况下才会回收；如下代码可以创建一个软引用：
    ```
    Integer prime = 1;  
    SoftReference<Integer> soft = new SoftReference<Integer>(prime);
    prime = null;
    ```
    - 弱引用. 当一个对象仅仅被WeakReference引用时，在下个垃圾收集周期时候该对象就会被回收。当把prime赋值为null的时候，原prime对象会在下一个垃圾收集周期中被回收，因为已经没有强引用指向它。
    ```
    Integer prime = 1;  
    WeakReference<Integer> soft = new WeakReference<Integer>(prime);
    prime = null;
    
    ```
    
    