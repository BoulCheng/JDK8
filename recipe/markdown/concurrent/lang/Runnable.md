# Runnable

- The Runnable interface should be implemented by any class whose instances are intended to be executed by a thread. The class must define a method of no arguments called run.
- (实现该接口的类的实例用于通过线程执行，该类必须定义一个无参run方法)


- This interface is designed to provide a common protocol for objects that wish to execute code while they are active.
    - For example, Runnable is implemented by class Thread. 
    - Being active simply means that a thread has been started and has not yet been stopped. 

- In addition, Runnable provides the means for a class to be active while not subclassing Thread.(此外，Runnable提供了在不子类化Thread的情况下激活类的方法。)
    - A class that implements Runnable can run without subclassing Thread by instantiating a Thread instance and passing itself in as the target
    - (实现Runnable的类可以通过实例化Thread实例并将自身作为目标传递进来，从而在不子类化Thread的情况下运行)
    
    - In most cases, the Runnable interface should be used if you are only planning to override the run() method and no other Thread methods.
    - This is important because classes should not be subclassed unless the programmer intends on modifying or enhancing the fundamental behavior of the class
    
- run()
    - the object's run method to be called in that separately executing thread when the thread start().
