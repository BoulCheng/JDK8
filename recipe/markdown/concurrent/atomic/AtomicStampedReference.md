# AtomicStampedReference

- CAS操作可能存在ABA的问题，就是说：假如一个值原来是A，变成了B，又变成了A，那么CAS检查时会发现它的值没有发生变化，但是实际上却变化了。

- 使用版本号。在变量前面追加上版本号，每次变量更新的时候把版本号加一，那么A－B－A 就会变成1A - 2B - 3A。

- 新旧对象引用需要相同
    ```
    public static void main(String[] args) {
        AtomicStampedReference<Foo> asr = new AtomicStampedReference<>(new Foo(), 0);
        int[] stamp = new int[1];
        Foo  oldRef = asr.get(stamp);
        // 调用get方法获取引用对象和对应的版本号
        int oldStamp = stamp[0];
        // stamp[0]保存版本号
        boolean b = asr.compareAndSet(oldRef, new Foo(), oldStamp, oldStamp + 1);
        System.out.println(b);
        //尝试以CAS方式更新引用对象，并将版本号+1
    }

    static class Foo {

    }
    ```