# ArrayBlockingQueue

- A bounded blocking queue backed by an array.(有界)

- This queue orders elements FIFO (first-in-first-out). 

- This is a classic "bounded buffer", in which a fixed-sized array holds elements inserted by producers and extracted by consumers.

- Once created, the capacity cannot be changed.

- Attempts to put an element into a full queue will result in the operation blocking; attempts to take an element from an empty queue will similarly block.

- This class supports an optional fairness policy for ordering waiting producer and consumer threads. By default, this ordering is not guaranteed. However, a queue constructed with fairness set to true grants threads access in FIFO order. Fairness generally decreases throughput but reduces variability and avoids starvation.

- (队列的元素是FIFO, 放入取出元素的线程可以实现fairness也可以不(默认))

- put、take共用一个ReentrantLock

- (量小使用，量大使用LinkedBlockingQueue)