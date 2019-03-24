# Thread

- public class Thread extends Object implements Runnable 实现了Runnable接口

- A thread is a thread of execution in a program. The Java Virtual Machine allows an application to have multiple threads of execution running concurrently.

- priority
    - Threads with higher priority are executed in preference to threads with lower priority.
    - When code running in some thread creates a new Thread object, the new thread has its priority initially set equal to the priority of the creating thread, and is a daemon thread if and only if the creating thread is a daemon.
    - Each thread may or may not also be marked as a daemon. 
    
- When a Java Virtual Machine starts up
    - there is usually a single non-daemon thread (which typically calls the method named main of some designated class).
    - The Java Virtual Machine continues to execute threads until either of the following occurs:
        - The exit method of class Runtime has been called and the security manager has permitted the exit operation to take place.
        - All threads that are not daemon threads have died, either by returning from the call to the run method or by throwing an exception that propagates beyond the run method.
        - (不是守护进程线程的所有线程都已死亡，要么从对run方法的调用返回，要么抛出一个在run方法之外传播的异常。)
        
- There are two ways to create a new thread of execution.
    -  One is to declare a class to be a subclass of Thread.
        ```
         //This subclass should override the run method of class Thread. 
         class PrimeThread extends Thread {
             long minPrime;
             PrimeThread(long minPrime) {
                 this.minPrime = minPrime;
             }
    
             public void run() {
                 // compute primes larger than minPrime
                  . . .
             }
         }
     
        ```
        ```
         //An instance of the subclass can then be allocated and started
         //
         PrimeThread p = new PrimeThread(143);
         p.start();
        ```
    - The other way to create a thread is to declare a class that implements the Runnable interface. That class then implements the run method. 
    - An instance of the class can then be allocated, passed as an argument when creating Thread, and started
        ```
         class PrimeRun implements Runnable {
             long minPrime;
             PrimeRun(long minPrime) {
                 this.minPrime = minPrime;
             }
    
             public void run() {
                 // compute primes larger than minPrime
                  . . .
             }
         }
        ```
        ```
         PrimeRun p = new PrimeRun(143);
         new Thread(p).start();
        ```    
        
- Every thread has a name for identification purposes. More than one thread may have the same name. If a name is not specified when a thread is created, a new name is generated for it.

- Java的线程分为两种：User Thread(用户线程)、DaemonThread(守护线程)。
    - 只要当前JVM实例中尚存任何一个非守护线程没有结束，守护线程就全部工作；只有当最后一个非守护线程结束时，守护线程随着JVM一同结束工作，Daemon作用是为其他线程提供便利服务，守护线程最典型的应用就是GC(垃圾回收器)，他就是一个很称职的守护者。
      
