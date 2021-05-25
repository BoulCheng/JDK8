


- 完成通知
- 异步执行
    - 多个操作串行执行
        - 一个接一个执行，上一个执行完成再执行下一个
    - 多个操作并行执行(合并多个CompletableFuture)
        - 一个执行完就结束
        - 所有执行完结束
    
- xxx()：表示该方法将继续在已有的线程中执行；
  xxxAsync()：表示将异步在线程池中执行
  
- CompletableFuture 可以指定异步处理流程：
thenAccept()处理正常结果；
exceptional()处理异常结果；
thenApplyAsync()用于串行化另一个CompletableFuture；
anyOf()和allOf()用于并行化多个CompletableFuture

- [https://www.liaoxuefeng.com/wiki/1252599548343744/1306581182447650]  