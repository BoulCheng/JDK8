/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.lang.ref;

/**
 * Reference queues, to which registered reference objects are appended by the
 * garbage collector after the appropriate reachability changes are detected.
 *
 * @author   Mark Reinhold
 * @since    1.2
 */

/**
 * 通常我们将其ReferenceQueue翻译为引用队列，换言之就是存放引用的队列，保存的是Reference对象。
 * 其作用在于Reference对象所引用的对象被GC回收时，该Reference对象将会被加入引用队列中（ReferenceQueue）的队列末尾
 *
 * 同样弱引用也可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被GC回收了，Java虚拟机就会把这个弱引用加入到与之关联的引用队列中，以便在恰当的时候将该弱引用回收。
 *
 *
 * 弱引用具有更短的生命.GC在扫描的过程中，一旦发现只具有被弱引用关联的对象，都会回收掉被弱引用关联的对象。换言之，无论当前内存是否紧缺，GC都将回收被弱引用关联的对象
 * GC在扫描 ？ 什么时候触发 ？ 内存不紧缺的时候也会触发 合适触发 ？
 *
 * 触发主GC（Garbage Collector，垃圾回收）的条件：
 * （1）当应用程序空闲时，即没有应用线程在运行时，GC会被调用。
 * （2）Java堆内存不足时，GC会被调用。
 *  (3) System.gc() suggests  , hint
 *
 *
 *
 *  Refrence 和引用队列 ReferenceQueue 联合使用时，如果 Refrence持有的对象被垃圾回收，Java 虚拟机就会把这个弱引用加入到与之关联的引用队列中。
 *
 *  Reference
 * 主要是负责内存的一个状态，当然它还和java虚拟机，垃圾回收器打交道。Reference类首先把内存分为4种状态Active，Pending，Enqueued，Inactive
 *
 * ReferenceQueue
 * 引用队列，在检测到适当的可到达性更改后，垃圾回收器将已注册的引用对象添加到队列中，ReferenceQueue实现了入队（enqueue）和出队（poll），还有remove操作，内部元素head就是泛型的Reference。
 *
 * 当我们想检测一个对象是否被回收了，那么我们就可以采用 Reference + ReferenceQueue，大概需要几个步骤：
 * 创建一个引用队列
 * ReferenceQueue queue = new ReferenceQueue();
 *
 * // 创建弱引用，此时状态为Active，并且Reference.pending为空，当前Reference.queue = 上面创建的queue，并且next=null
 * WeakReference reference = new WeakReference(new Object(), queue);
 * System.out.println(reference);
 * // 当GC执行后，由于是弱引用，所以回收该object对象，并且置于pending上，此时reference的状态为PENDING
 * System.gc();
 *
 * // ReferenceHandler从pending中取下该元素，并且将该元素放入到queue中，此时Reference状态为ENQUEUED，Reference.queue = ReferenceENQUEUED
 *
 * // 当从queue里面取出该元素，则变为INACTIVE，Reference.queue = Reference.NULL
 * Reference reference1 = queue.remove();
 * System.out.println(reference1);
 *
 * 那这个可以用来干什么了？
 * 可以用来检测内存泄露， github 上面 的 leekCanary 就是采用这种原理来检测的
 *
 *
 * Java中有几种不同的引用方式，它们分别是：强引用、软引用、弱引用和虚引用。下面，我们首先详细地了解下这几种引用方式的意义。
 * 强引用
 *
 * 在此之前我们介绍的内容中所使用的引用都是强引用，这是使用最普遍的引用。如果一个对象具有强引用，那就类似于必不可少的生活用品，垃圾回收器绝不会回收它。当内存空 间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，也不会靠随意回收具有强引用的对象来解决内存不足问题。
 *
 *
 * 软引用（SoftReference）
 *
 * SoftReference 类的一个典型用途就是用于内存敏感的高速缓存。SoftReference 的原理是：在保持对对象的引用时保证在 JVM 报告内存不足情况之前将清除所有的软引用。关键之处在于，垃圾收集器在运行时可能会（也可能不会）释放软可及对象。对象是否被释放取决于垃圾收集器的算法 以及垃圾收集器运行时可用的内存数量。
 *
 *
 * 弱引用（WeakReference）
 *
 * WeakReference 类的一个典型用途就是规范化映射（canonicalized mapping）。另外，对于那些生存期相对较长而且重新创建的开销也不高的对象来说，弱引用也比较有用。关键之处在于，垃圾收集器运行时如果碰到了弱可及对象，将释放 WeakReference 引用的对象。然而，请注意，垃圾收集器可能要运行多次才能找到并释放弱可及对象。
 *
 *
 * 虚引用（PhantomReference）
 *
 * PhantomReference 类只能用于跟踪对被引用对象即将进行的收集。同样，它还能用于执行 pre-mortem 清除操作。PhantomReference 必须与 ReferenceQueue 类一起使用。需要 ReferenceQueue 是因为它能够充当通知机制。当垃圾收集器确定了某个对象是虚可及对象时，PhantomReference 对象就被放在它的 ReferenceQueue 上。将 PhantomReference 对象放在 ReferenceQueue 上也就是一个通知，表明 PhantomReference 对象引用的对象已经结束，可供收集了。这使您能够刚好在对象占用的内存被回收之前采取行动。Reference与ReferenceQueue的配合使用。
 *
 * GC、Reference与ReferenceQueue的交互
 * A、 GC无法删除存在强引用的对象的内存。
 *
 * B、 GC发现一个只有软引用的对象内存，那么：
 *
 * ① SoftReference对象的referent 域被设置为null，从而使该对象不再引用heap对象。
 *
 * ② SoftReference引用过的heap对象被声明为finalizable。
 *
 * ③ 当 heap 对象的 finalize() 方法被运行而且该对象占用的内存被释放，SoftReference 对象就被添加到它的 ReferenceQueue（如果后者存在的话）。
 *
 *
 * C、 GC发现一个只有弱引用的对象内存，那么：
 *
 * ① WeakReference对象的referent域被设置为null,从而使该对象不再引用heap对象。
 *
 * ② WeakReference引用过的heap对象被声明为finalizable。
 *
 * ③ 当heap对象的finalize()方法被运行而且该对象占用的内存被释放时，WeakReference对象就被添加到它的ReferenceQueue（如果后者存在的话）。
 *
 *
 * D、 GC发现一个只有虚引用的对象内存，那么：
 *
 * ① PhantomReference引用过的heap对象被声明为finalizable。
 *
 * ② PhantomReference在堆对象被释放之前就被添加到它的ReferenceQueue。
 *
 * 值得注意的地方有以下几点：
 * 1、GC在一般情况下不会发现软引用的内存对象，只有在内存明显不足的时候才会发现并释放软引用对象的内存。
 * 2、GC对弱引用的发现和释放也不是立即的，有时需要重复几次GC，才会发现并释放弱引用的内存对象。
 * 3、软引用和弱引用在添加到ReferenceQueue的时候，其指向真实内存的引用已经被置为空了，相关的内存也已经被释放掉了。而虚引用在添加到ReferenceQueue的时候，内存还没有释放，(仍然可以对其进行访问, 纠正通过get直接返回null不可访问)。
 *
 *
 * SoftReference具有构建Cache系统的特质，因此我们可以结合哈希表实现一个简单的缓存系统。这样既能保证能够尽可能多的缓存信息，又可以保证Java虚拟机不会因为内存泄露而抛出OutOfMemoryError。这种缓存机制特别适合于内存对象生命周期长，且生成内存对象的耗时比较长的情况，例如缓存列表封面图片等。对于一些生命周期较长，但是生成内存对象开销不大的情况，使用WeakReference能够达到更好的内存管理的效果
 * @param <T>
 */
public class ReferenceQueue<T> {

    /**
     * Constructs a new reference-object queue.
     */
    public ReferenceQueue() { }

    private static class Null<S> extends ReferenceQueue<S> {
        boolean enqueue(Reference<? extends S> r) {
            return false;
        }
    }

    static ReferenceQueue<Object> NULL = new Null<>();
    static ReferenceQueue<Object> ENQUEUED = new Null<>();

    static private class Lock { };
    private Lock lock = new Lock();
    private volatile Reference<? extends T> head = null;
    private long queueLength = 0;

    boolean enqueue(Reference<? extends T> r) { /* Called only by Reference class */
        synchronized (lock) {
            // Check that since getting the lock this reference hasn't already been
            // enqueued (and even then removed)
            ReferenceQueue<?> queue = r.queue;
            if ((queue == NULL) || (queue == ENQUEUED)) {
                return false;
            }
            assert queue == this;
            r.queue = ENQUEUED;
            r.next = (head == null) ? r : head;
            head = r;
            queueLength++;
            if (r instanceof FinalReference) {
                sun.misc.VM.addFinalRefCount(1);
            }
            lock.notifyAll();
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    private Reference<? extends T> reallyPoll() {       /* Must hold lock */
        Reference<? extends T> r = head;
        if (r != null) {
            head = (r.next == r) ?
                null :
                r.next; // Unchecked due to the next field having a raw type in Reference
            r.queue = NULL;
            r.next = r;
            queueLength--;
            if (r instanceof FinalReference) {
                sun.misc.VM.addFinalRefCount(-1);
            }
            return r;
        }
        return null;
    }

    /**
     * Polls this queue to see if a reference object is available.  If one is
     * available without further delay then it is removed from the queue and
     * returned.  Otherwise this method immediately returns <tt>null</tt>.
     *
     * @return  A reference object, if one was immediately available,
     *          otherwise <code>null</code>
     */
    public Reference<? extends T> poll() {
        if (head == null)
            return null;
        synchronized (lock) {
            return reallyPoll();
        }
    }

    /**
     * Removes the next reference object in this queue, blocking until either
     * one becomes available or the given timeout period expires.
     *
     * <p> This method does not offer real-time guarantees: It schedules the
     * timeout as if by invoking the {@link Object#wait(long)} method.
     *
     * @param  timeout  If positive, block for up to <code>timeout</code>
     *                  milliseconds while waiting for a reference to be
     *                  added to this queue.  If zero, block indefinitely.
     *
     * @return  A reference object, if one was available within the specified
     *          timeout period, otherwise <code>null</code>
     *
     * @throws  IllegalArgumentException
     *          If the value of the timeout argument is negative
     *
     * @throws  InterruptedException
     *          If the timeout wait is interrupted
     */
    public Reference<? extends T> remove(long timeout)
        throws IllegalArgumentException, InterruptedException
    {
        if (timeout < 0) {
            throw new IllegalArgumentException("Negative timeout value");
        }
        synchronized (lock) {
            Reference<? extends T> r = reallyPoll();
            if (r != null) return r;
            long start = (timeout == 0) ? 0 : System.nanoTime();
            for (;;) {
                lock.wait(timeout);
                r = reallyPoll();
                if (r != null) return r;
                if (timeout != 0) {
                    long end = System.nanoTime();
                    timeout -= (end - start) / 1000_000;
                    if (timeout <= 0) return null;
                    start = end;
                }
            }
        }
    }

    /**
     * Removes the next reference object in this queue, blocking until one
     * becomes available.
     *
     * @return A reference object, blocking until one becomes available
     * @throws  InterruptedException  If the wait is interrupted
     */
    public Reference<? extends T> remove() throws InterruptedException {
        return remove(0);
    }

}
