## 第五节 Spring WebFlux 运用

### 关于 Reactive 的一些讲法

>其中笔者挑选了以下三种出镜率最高的讲法
>- Reactive 是异步非阻塞编程：错误，Reactive是 同步/异步非阻塞编程模型；
>- Reactive 是同步/异步非阻塞编程：大多数情况是没有的，少数可能能会，[参考测试用例地址](https://blog.ippon.tech/spring-5-webflux-performance-tests/)
>- Reactive 解决传统编程模型遇到的困境：错误，传统困境不需要，也不能被 Reactive解决；

>传统编程模型中遇到的某些困境：Reactor 认为阻塞可能是浪费的，将以上 Reactor 观点归纳如下，它认为：
>- 阻塞导致性能瓶颈和浪费资源：
>>- 任何代码都是阻塞的，因为指令是串行的；
>>- 非阻塞从实现来说，就是回调(当前不阻塞，事后 数据要操作的数据准备好了 再来执行)，非阻塞(Spring事件就是 非阻塞)
>- 增加线程可能会引起资源竞争和并发问题：通用问题
>- 并行的方式不是银弹，不能解决所有问题，废话


```puml
title 阻塞
load -> doLoad
doLoad -> loadConfigurations : 耗时 1s
loadConfigurations -> loadUsers : 耗时 2s
loadUsers -> loadOrders : 耗时 3s
```
```puml
title 非阻塞
load -> doLoad
doLoad -> loadConfigurations : 耗时 1s
doLoad -> loadUsers : 耗时 2s
doLoad -> loadOrders : 耗时 3s
```
`同步 代码见 spring-reactive项目 DataLoader类，异步 代码见ParallelDataLoader`

>同步/异步 非阻塞
>- 同步回调非阻塞(回调实现非阻塞)
>- 异步回调非阻塞(回调实现非阻塞)
>- `代码见 spring-reactive项目 SpringEventDemo类`


>Reactor 认为异步不一定能够救赎，再次将以上观点归纳，它认为：
>- Callbacks 是解决非阻塞的方案，然而他们之间很难组合，并且快速地将代码引导至 "Callback Hell" 的不归路
>- Futures  相对于 Callbacks 好一点，不过还是无法组合，不过  CompletableFuture 能够提升这方面的不足



### CompletableFuture
> Future 的局限性：
>- future.get() 方法是阻塞的；
>- Future 没有办法组合；

> CompletableFuture特点：
>- 任务Future之间有依赖关系，第一步的结果，是第二步的输入；
>- 提供异步操作，提供 Future 链式操作，提供函数式编程；
>- 函数式编程 + Reactive：重点；
```java
public class CompletableFutureDemo {
    public static void main(String[] args) {
        println("当前线程");
        CompletableFuture.supplyAsync(() -> {
           println("第一步");
           return "Hello";                          //第一步返回 Hello
        }).thenApplyAsync(result -> {
            println("第二步");
            return result + " World";               //第二步以第一步返回结果 为输入
        }).thenAccept(CompletableFutureDemo::println)
        .join();                                    //等待Thread-0线程执行结束，主线程再继续执行，类似 CountDownLatch
    }
    private static void println(String message) {
        System.out.printf("[线程 : %s] %s\n",Thread.currentThread().getName(), message);
    }
}
/**
返回结果如下，虽然是异步操作，但是 都是 同一个线程 Thread-0执行的
*[线程 : main] 当前线程
*[线程 : Thread-0] 第一步
*[线程 : Thread-0] 第二步
*[线程 : Thread-0] Hello World
*/
```

```puml
title 代码表面
main -> supplyAsync
main -> thenApplyAsync
main -> thenAccept
```
>* supplyAsync()方法，thenApplyAsync()，thenAccept()是并行的，但是 `后面的方法要等前面的返回结果`，因此 `并行 相当于 串行`

```puml
title 实质
main -> supplyAsync
supplyAsync -> thenApplyAsync : thenApplyAsync方法 以前一步supplyAsync方法 输出结果为 输入参数，使用多线程没意义
thenApplyAsync -> thenAccept
```
>结果分析：虽然是异步操作，但是 都是 同一个线程 Thread-0执行的，为什么呢？
>- 如上图所示，main()方法调用 supplyAsync()方法，然后main()方法再调用thenApplyAsync()方法，后一个方法thenApplyAsync()方法 依赖前一个方法supplyAsync()的返回值，因此，异步切换线程 没有意义

### Reactive + 函数式编程
> 传统三段式编程 属于 命令式编程(Imperative programming) 即一行一行的执行，属于非流式
>- 业务执行
>- 业务执行完成
>- 异常处理
```text
    try {
        业务执行
    }catch (Exception e){
        异常处理
    }finally {
        业务执行完成
    }
```

> Reactive 编程 属于 流式编程，Fluent 流畅的，Streams 流式的，好处是什么？
> 大多数业务逻辑属于 数据操作，数据操作有如下几种类型：
>- 消费 数据：Consumer
>- 转换 数据类型：Function
>- 增加/减少 数据维度：map/flatMap/reduce
>- 业务效果：实现业务流程编排


> 函数式语言特性（Java 8+）
>- 生产类型 Supplier
>- 转换类型  Function
>- 消费类型  Consumer
>- 判断类型 Predicate
>- 提升/减少维度 map/reduce/flatMap

`代码见 spring-reactive项目 StreamDemo类 `
```java
/**java,c#,js,python,scala,koltin 都使用 Reactive + Stream的模式*/
public class StreamDemo {
    public static void main(String[] args) {
        Stream.of(0,1,2,3,4,5,6,7,8,9)              //Supplier 生产数据
                .filter(v -> v % 2 == 0)            //Predicate 判断数据
                .map(v -> v + 1)                    //Function 改变数据维度
                .reduce(Integer::sum)               //聚合操作
                .ifPresent(System.out::println);    //Consumer 消费数据
//                .forEach(System.out::println);      //Consumer
    }
}
```
```text
Stream 是 Iterator 模式，数据已完全准备，Pull拉模式；
Reactive 是观察者模式，数据来一个算一个，Push推模式，当有数据变化的时候，作出反应（Reactor）
```
React（反应）


WebFlux 使用场景

长期异步执行，一旦提交，慢慢操作。是否适合 RPC 操作？

任务型的，少量线程，多个任务长时间运作，达到伸缩性。

Mono：单数据 Optional 0:1, RxJava : Single

Flux : 多数据集合，Collection 0:N , RxJava : Observable


函数式编程
非阻塞（同步/异步）
远离 Servlet API


API
Servlet
HttpServletRequest


不再强烈依赖 Servlet 容器（兼容）


容器
Tomcat
Jetty




Spring Cloud Gateway -> Reactor

Spring WebFlux -> Reactor

Zuul2 -> Netty  Reactive


WebFlux 整体架构


相关视频


公开课


高并发系列


Java 8 异步并发编程
Java 9 异步并发编程
Reactor Streams 并发编程之 Reactor
Vert.x 异步编程
异步事件驱动 Web 开发
响应式应用架构重构





## 回顾 Spring Web MVC



| Bean type                                                    | Explanation                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [HandlerMapping](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-handlermapping) | Map a request to a handler along with a list of [interceptors](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor) for pre- and post-processing. The mapping is based on some criteria the details of which vary by `HandlerMapping` implementation.The two main `HandlerMapping` implementations are `RequestMappingHandlerMapping` which supports `@RequestMapping`annotated methods and `SimpleUrlHandlerMapping` which maintains explicit registrations of URI path patterns to handlers. |
| HandlerAdapter                                               | Help the `DispatcherServlet` to invoke a handler mapped to a request regardless of how the handler is actually invoked. For example, invoking an annotated controller requires resolving annotations. The main purpose of a `HandlerAdapter` is to shield the `DispatcherServlet` from such details. |
| [HandlerExceptionResolver](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-exceptionhandlers) | Strategy to resolve exceptions possibly mapping them to handlers, or to HTML error views, or other. See [Exceptions](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-exceptionhandlers). |
| [ViewResolver](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-viewresolver) | Resolve logical String-based view names returned from a handler to an actual `View` to render to the response with. See [View Resolution](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-viewresolver)and [View Technologies](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-view). |
| [LocaleResolver](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-localeresolver), [LocaleContextResolver](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-timezone) | Resolve the `Locale` a client is using and possibly their time zone, in order to be able to offer internationalized views. See [Locale](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-localeresolver). |
| [ThemeResolver](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-themeresolver) | Resolve themes your web application can use, for example, to offer personalized layouts. See [Themes](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-themeresolver). |
| [MultipartResolver](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-multipart) | Abstraction for parsing a multi-part request (e.g. browser form file upload) with the help of some multipart parsing library. See [Multipart resolver](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-multipart). |
| [FlashMapManager](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-flash-attributes) | Store and retrieve the "input" and the "output" `FlashMap` that can be used to pass attributes from one request to another, usually across a redirect. See [Flash attributes](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-flash-attributes). |



`HandlerInterceptor` : 前置、后置处理、完成阶段（异常处理）

* 前置：pre- before-
* 后置：post- after-
* 完成：`finally`
  * `org.springframework.web.servlet.HandlerInterceptor#afterCompletion`
  * `java.util.concurrent.CompletableFuture#whenComplete`

`HandlerMapping` :

* 实现
  * `AbstractHandlerMapping` (必看)
    * `AbstractHandlerMethodMapping` 获取 `HandlerMethod`
      * `RequestMappingInfoHandlerMapping`
        * `RequestMappingHandlerMapping` 返回 `HandlerMethod`
          * 前提：当 `@Controller` 方法上面标注了 `@RequestMapping`
            * `HandlerMethod` -> 执行方法？
            * 通过请求定位 `@RequestMapping` -> URI
    * `AbstractUrlHandlerMapping` 获取 Handler（注意返回类型）
      * `SimpleUrlHandlerMapping` 返回 `Object`
  * `RequestMappingHandlerMapping `
    * `@RequestMapping `
  * `SimpleUrlHandlerMapping `

*  包含 `HandlerInterceptor` 集合
  * 责任链
    * 区别 `Filter`
      * `HandlerInterceptor` 采用返回值
      * `Filter`  采用 `FilterChain`
        * 最终节点 Servlet
  * 拦截链条
  * 各司其职
    * 顺序
* 作为 `DispatcherServlet` 一种`HandlerMapping`
  * `DispatcherServlet` 关联多个 `HandlerMapping`
    * `DispatcherServlet` ：  `HandlerMapping` = 1 : N
    * `HandlerMapping` ：  `HandlerInterceptor` = 1 : N
      * 要经过筛选 `HandlerExecutionChain`
        * 一个 Handler
          * 猜测一：`@Controller`
          * 猜测二：`@Exceptionhandler `
          * ~~猜测三：`HttpServletRequest`~~
          * `HandlerMethod` ？
        * `HandlerInterceptor` List
    * 问题：多个 `HandlerMapping` 谁被选择
      * 可能猜想点
        * `Ordered` 接口参考顺序
        * 哪个 `HandlerMapping` 被请求规则匹配了



```java
String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
```



HTTP R -> http://www.acme.com/abc/def

URI -> /abc/def



### `HandlerMethod` 初始化过程

*  Spring 应用上下文获取所有的 Bean
  * 筛选出标注 `@Controller` 或者 `@RequestMapping` 的 Bean
    * 再次筛选标准 `@RequestMapping` 方法
      * 将该 Bean 和对应 `Method` 合并成 `HandlerMethod` 
        * 存入``HandlerMethod`` 集合





### `HandlerMethod` 定位过程

* ``HandlerMethod`` 集合查找 `@RequestMapping` 定义 URI
  * 返回 `HandlerMethod`



### `HandlerMapping` 和 `HandlerAdapter` 区别



 `HandlerMapping` 主要负责映射，一次 HTTP 请求找到对应（最佳）的 `HandlerMethod` 以及多个 `HandlerInterceptor` -> `HandlerExecutionChain`

```java
	@Nullable
	protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		if (this.handlerMappings != null) {
			for (HandlerMapping hm : this.handlerMappings) {
				if (logger.isTraceEnabled()) {
					logger.trace(
							"Testing handler map [" + hm + "] in DispatcherServlet with name '" + getServletName() + "'");
				}
				HandlerExecutionChain handler = hm.getHandler(request);
				if (handler != null) {
					return handler;
				}
			}
		}
		return null;
	}
```



`HandlerAdapter` 主要负责  Handler 执行后处理



Spring MVC 2.5 之前面向接口编程

* 页面渲染

  * HTML
    * `JstlView`
  * JSON
    * `MappingJackson2JsonView`

* 接口定义

  ```java
  public interface Controller {
  
  	ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
  
  }
  ```

  方法返回值 `ModelAndView`

  方法参数 `HttpServletRequest` 和 `HttpServletResponse`

* `HandlerMapping` 老实现：



Spring Web MVC 2.5+ 面向注解驱动编程

* `HandlerMethod`返回值：
  * `ModelAndView`
  * `String`
  * `ResponseEntity`
  * `void`
* `HandlerMethod`参数：
  * `@RequestParam`
  * `@RequestHeader`
  * `@PathVariable`
  * `@RequestBody`
  * `Model`
* `HandlerMapping` 新实现：``RequestMappingHandlerMapping``







`View` 页面渲染

`JstlView`



`@Controller` 处理方法可能不返回 `ModelAndView`

`HandlerMethod` ->  `ModelAndView`







`HandlerInterceptor` : 没有匹配请求

`MappedInterceptor` : 能够匹配请求





`@ExceptionHandler` : 



Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.error(javax.servlet.http.HttpServletRequest)



## 理解 WebFlux 实现



### `org.springframework.web.reactive.HandlerMapping`

对比参考 `org.springframework.web.servlet.HandlerMapping`





### `org.springframework.web.reactive.HandlerAdapter`

对比参考 `org.springframework.web.servlet.HandlerAdapter`



### `org.springframework.web.reactive.DispatcherHandler`

对比参考 `org.springframework.web.servlet.DispatcherServlet`





### Java 微服务实现方案



#### Vert.x



#### Spring Boot / Spring Cloud



#### Kuzz

## 扩展
>- 判断 是阻塞方法，还是非阻塞方法？
>> throws InterruptedException 声明抛出该异常的都是阻塞方法，未声明抛出该异常都是非阻塞方法