#  [第三节 REST](lesson-3)



## 主要内容



### Reactive 原理



#### 关于 Reactive 的一些讲法

其中笔者挑选了以下三种出镜率最高的讲法：

- Reactive 是异步非阻塞编程（错误）
  - Reactive 是同步/异步非阻塞编程
- Reactive 能够提升程序性能
  - 大多数情况是没有的，少数可能能会
    - 参考测试用例地址：https://blog.ippon.tech/spring-5-webflux-performance-tests/
- Reactive 解决传统编程模型遇到的困境
  - 也是错的，传统困境不需，也不能被 Reactive
  - 

### 传统编程模型中的某些困境



#### [Reactor](http://projectreactor.io/docs/core/release/reference/#_blocking_can_be_wasteful) 认为阻塞可能是浪费的

将以上 Reactor 观点归纳如下，它认为：

1. 阻塞导致性能瓶颈和浪费资源
   1. 任何代码都是阻塞（指令是串行）
   2. 非阻塞从实现来说，就是回调
      1. 当前不阻塞，事后来执行
2. 增加线程可能会引起资源竞争和并发问题
   1. 通用问题
3. 并行的方式不是银弹（不能解决所有问题）



```sequence
load() -> loadConfigurations(): 
loadConfigurations() -> loadUsers() :
loadUsers() -> loadOrders() : 
```

[Reactor](http://projectreactor.io/docs/core/release/reference/#_asynchronicity_to_the_rescue) 认为异步不一定能够救赎

再次将以上观点归纳，它认为：

- Callbacks 是解决非阻塞的方案，然而他们之间很难组合，并且快速地将代码引导至 "Callback Hell" 的不归路
- Futures  相对于 Callbacks 好一点，不过还是无法组合，不过  `CompletableFuture` 能够提升这方面的不足



### `CompletableFuture`

`Future` 限制

* `get()` 方法是阻塞的
* `Future` 没有办法组合
  * 任务`Future`之间由依赖关系
    * 第一步的结果，是第二部的输入

 `CompletableFuture`

* 提供异步操作
* 提供 `Future` 链式操作
* 提供函数式编程

```sequence
main() -> supplyAsync(): 异步操作

supplyAsync() -> thenApplyAsync() : 

thenApplyAsync() -> thenAccept() : 

```



### 函数式编程 + Reactive

* Reactive programming
* 编程风格
  * Fluent 流畅的
  * Streams 流式的
* 业务效果
  * 流程编排
  * 大多数业务逻辑是数据操作
* 函数式语言特性（Java 8+）
  * 消费类型  `Consumer`
  * 生产类型 `Supplier`
  * 转换类型  `Function`
  * 判断类型 `Predicate`
  * 提升/减少维度 `map`/`reduce`/`flatMap`

```java
        // 是不是非常直观? Java/C#/JS/Python/Scale/Koltin -> Reactive/Stream
        Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) // 0-9 集合
                .filter(v -> v % 2 == 1) // 判断数值->获取奇数
                .map(v -> v - 1) // 奇数变偶数
                .reduce(Integer::sum) // 聚合操作
                .ifPresent(System.out::println) // 输出 0 + 2 + 4 + 6 + 8
```



Stream 是 `Iterator` 模式，数据已完全准备，拉模式（Pull）

Reactive 是观察者模式，来一个算一个，推模式（Push），当有数据变化的时候，作出反应（Reactor）

React（反应）



### WebFlux 使用场景



长期异步执行，一旦提交，慢慢操作。是否适合 RPC 操作？

任务型的，少量线程，多个任务长时间运作，达到伸缩性。



`Mono`：单数据 `Optional` 0:1, RxJava : `Single`

`Flux` : 多数据集合，`Collection` 0:N , RxJava : `Observable`



* 函数式编程
* 非阻塞（同步/异步）
* 远离 Servlet API
  * API
    - `Servlet`
    - `HttpServletRequest`
* 不再强烈依赖 Servlet 容器（兼容）
  * 容器
    * Tomcat
    * Jetty



Spring Cloud Gateway -> Reactor

Spring WebFlux -> Reactor

Zuul2 -> Netty  Reactive



### WebFlux 整体架构







## 相关视频

### 公开课

#### 高并发系列

* Java 8 异步并发编程

* Java 9 异步并发编程

* Reactor Streams 并发编程之 Reactor

* Vert.x 异步编程

* 异步事件驱动 Web 开发

* 响应式应用架构重构

  







## 下节课程预习内容

### 理论

* 









