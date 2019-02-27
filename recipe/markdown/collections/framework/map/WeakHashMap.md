# WeakHashMap

- An entry in a WeakHashMap will automatically be removed when its key is no longer in ordinary use. More precisely, the presence of a mapping for a given key will not prevent the key from being discarded by the garbage collector

- When a key has been discarded its entry is effectively removed from the map, so this class behaves somewhat differently from other Map implementations

- Both null values and the null key are supported

- This class is intended primarily for use with key objects whose equals methods test for object identity using the == operator. Once such a key is discarded it can never be recreated. (i.e., equals方法使用==操作, key1 == key2, key2 can not be recreated if key1 be discarded.)

- This class will work perfectly well with key objects whose equals methods are not based upon object identity, such as String instances. With such recreatable key objects, however, the automatic removal of WeakHashMap entries whose keys have been discarded may prove to be confusing.(i.e., 用String的实例作为key可以很好地处理，因为String重写了equals方法，相同的字符串可以表示为不同的实例，也即不同的key，但同时相同的字符串key可以被销毁继而被创建也会导致混淆)

- The behavior of the WeakHashMap class depends in part upon the actions of the garbage collector.a WeakHashMap may behave as though an unknown thread is silently removing entries

- Each key object in a WeakHashMap is stored indirectly as the referent of a weak reference. Therefore a key will automatically be removed only after the weak references to it, both inside and outside of the map, have been cleared by the garbage collector.

- The value objects in a WeakHashMap are held by ordinary strong references. Thus care should be taken to ensure that value objects do not strongly refer to their own keys, either directly or indirectly, since that will prevent the keys from being discarded.

- **extension**
    - [RefPackageDescription](../../../ref/RefPackageDescription.md)
    
- **complex**
    - *Note that a value object may refer indirectly to its key via the WeakHashMap itself; that is, a value object may strongly refer to some other key object whose associated value object, in turn, strongly refers to the key of the first value object. If the values in the map do not rely on the map holding strong references to them, one way to deal with this is to wrap values themselves within WeakReferences before inserting, as in: m.put(key, new WeakReference(value)), and then unwrapping upon each get.*