# ScheduledThreadPoolExecutor


- 实现原理
    - 延时任务，计算出task的下次触发时间时间戳
    - 放入优先级阻塞队列，以触发时间时间戳比较大小
    - 线程池线程取出优先级阻塞队列中的任务，会比较优先级阻塞队列中最小元素即任务的触发时间时间戳和当前时间大小关系，进而判断是立即触发还是继续等待
        ```
                public long getDelay(TimeUnit unit) {
                    return unit.convert(time - now(), NANOSECONDS);
                }
        
        ```    
     - 如果是周期性任务，每次从优先级阻塞队列取出任务执行后重新计算下一次触发时间再放入队列
     - 关键源码
        ```
                /**
                 * Sets the next time to run for a periodic task.
                 */
                private void setNextRunTime() {
                    long p = period;
                    if (p > 0)
                        time += p;
                    else
                        time = triggerTime(-p);
                }
        
        ```    
        ```
            long triggerTime(long delay) {
                return now() +
                    ((delay < (Long.MAX_VALUE >> 1)) ? delay : overflowFree(delay));
            }
        ```
- 四个构造函数keepAliveTime都为0且maximumPoolSize都为Integer.MAX_VALUE，但是阻塞队列是无界队列，因此线程池中最多有核心线程数个线程