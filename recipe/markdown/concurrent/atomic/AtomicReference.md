# AtomicReference

- 以原子方式更新对象引用
- 以无锁方式访问共享资源
- AtomicReference#compareAndSet(expect, update)  新旧引用相同才会更新
    - Atomically sets the value to the given updated value if the current value {@code ==} the expected value.
- 自旋+CAS的无锁操作保证共享变量的线程安全
    ```
    public class AtomicRefTest {
        public static void main(String[] args) throws InterruptedException {
            AtomicReference<Integer> ref = new AtomicReference<>(new Integer(1000));
    
            List<Thread> list = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                Thread t = new Thread(new Task(ref), "Thread-" + i);
                list.add(t);
                t.start();
            }
    
            for (Thread t : list) {
                t.join();
            }
    
            System.out.println(ref.get());    // 打印2000
        }
        
        
    
        static class Task implements Runnable {
            private AtomicReference<Integer> ref;
        
            Task(AtomicReference<Integer> ref) {
                this.ref = ref;
            }
            
            @Override
            public void run() {
                //自旋+CAS的无锁操作保证共享变量的线程安全
                for (; ; ) {    //自旋操作
                    Integer oldV = ref.get();   
                    if (ref.compareAndSet(oldV, oldV + 1))  // CAS操作 
                        break;
                }
            }
        }
    
    }
    ```