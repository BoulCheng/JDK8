




## park/unpark
- park/unpark的设计原理核心是“许可”：park是等待一个许可，unpark是为某线程提供一个许可。如果某线程A调用park，那么除非另外一个线程调用unpark(A)给A一个许可，否则线程A将阻塞在park操作上
- unpark操作可以再park操作之前
- “许可”是不能叠加的，“许可”是一次性的。
    - 比如线程B连续调用了三次unpark函数，当线程A调用park函数就使用掉这个“许可”，如果线程A再次调用park，则进入等待状态

- 底层实现原理
    - Posix线程库pthread中的 mutex（互斥量），condition（条件变量）来实现的，共同保护保护了一个 _counter 的int变量，当park时，这个变量被设置为0，当unpark时，这个变量被设置为1
    
- park
    - counter > 0 return
    - 否则 condition条件等待，线程阻塞，pthread_cond_wait (_cond, _mutex) 会释放unlock mutex ;
        - 唤醒前会lock mutex，唤醒后 counter设置为0，再unlock mutex
- unpark
    - counter > 0情况(即重复许可多次unpark)，lock mutex，设置 _counter 为1，再unlock mutex返回
    - counter < 1，lock mutex，设置 _counter 为1，调用pthread_cond_signal唤醒在park中等待的线程，pthread_cond_signal (_cond)，再unlock mutex返回
    
    
    
### cas
- CAS 是 CPU指令级别实现了原子性的比较和交换(Conmpare And Swap)操作，注意CAS不是锁只是CPU提供的一个原子性操作指令(处理器指令)
- ABA问题、性能开销问题、只能保证一个共享变量之间的原则性操作问题
- 并不是说 CAS 一定比SYN好，如果高并发执行时间久 ，用SYN好， 因为SYN底层用了wait() 阻塞后是不消耗CPU资源的。如果锁竞争不激烈说明自旋不严重，此时用CAS             