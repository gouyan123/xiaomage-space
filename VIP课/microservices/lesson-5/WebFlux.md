# Spring WebFlux 运用
## Spring5及Reactive背景
```text
2017年Java技术生态中，最具影响力的发布莫过于Java9和Spring5，前者主要支持模块化，次要地提供了Flow API的支持，后者将"身家性命"压在Reactive上面，认为Reactive是未来的趋势，它以Reactive框架Reactor为基础，
逐步构建一套完整的Reactive技术栈，其中以 WebFlux技术最为引人关注，作为替代Servlet Web的核心特性，承载了多年Spring逆转Java EE的初心。于是，业界开始大力的推广Reactive技术，Reactive的一些讲法如下。
```

## 关于 Reactive 的一些讲法
```text
Reactive文章链接：https://m.imooc.com/article/46306?utm_source=oschina-app
其中笔者挑选了以下三种出镜率最高的讲法：
1、Reactive 是异步非阻塞编程：
    错误，正确说法：是 同步+异步非阻塞编程；
2、Reactive 能够提升程序性能：小部分情况 能够提升程序性能；
    [参考测试用例地址](https://blog.ippon.tech/spring-5-webflux-performance-tests/)
    NIO实际属于同步非阻塞，AIO属于异步非阻塞；
3、Reactive 解决传统编程模型遇到的困境(重点)；
    也是错的，传统困境不需要Reactive解决，也不能被 Reactive解决；
    对于传统编程模型中的某些困境，Reactor 观点归纳如下：
        3.1 阻塞导致性能瓶颈和浪费资源：
            1 任何代码都是阻塞(指令是串行)；
            2 非阻塞从实现来说，就是回调；阻塞：要操作的数据还没准备好；回调即 线程当前不阻塞等待数据，数据准备好以后，执行回调处理准备好的数据；
        3.2 任何代码都是阻塞（指令是串行）
        3.3 非阻塞从实现来说，就是回调
```

```puml
A -> B: have()
```
[百度](https://www.baidu.com)
    [参考测试用例地址](https://blog.ippon.tech/spring-5-webflux-performance-tests/)

| id  | name  | age |
| --- | --- | --- |
| 23  | James  | 34 |
| 24  | Kobe  | 37 |

```java
public class User{

}
```





[HandlerMapping](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/web.html#mvc-handlermapping)

[百度](https://www.baidu.com/)

[参考测试用例地址](https://blog.ippo.tech/spring-5-webflux-performance-tests/)

增加线程可能会引起资源竞争和并发问题


通用问题


并行的方式不是银弹（不能解决所有问题）

```puml
 load() -> loadConfigurations(): loadConfigurations() -> loadUsers()
\: loadUsers() -> loadOrders() : 
```
Reactor 认为异步不一定能够救赎

再次将以上观点归纳，它认为：


Callbacks 是解决非阻塞的方案，然而他们之间很难组合，并且快速地将代码引导至 "Callback Hell" 的不归路
Futures  相对于 Callbacks 好一点，不过还是无法组合，不过  CompletableFuture 能够提升这方面的不足



CompletableFuture


Future 限制



get() 方法是阻塞的

Future 没有办法组合


任务Future之间由依赖关系
第一步的结果，是第二部的输入




CompletableFuture


提供异步操作
提供 Future 链式操作
提供函数式编程


main() -> supplyAsync(): 异步操作

supplyAsync() -> thenApplyAsync() : 

thenApplyAsync() -> thenAccept() : 


函数式编程 + Reactive


Reactive programming
编程风格


Fluent 流畅的
Streams 流式的


业务效果


流程编排
大多数业务逻辑是数据操作


函数式语言特性（Java 8+）


消费类型  Consumer

生产类型 Supplier

转换类型  Function

判断类型 Predicate

提升/减少维度 map/reduce/flatMap





        // 是不是非常直观? Java/C#/JS/Python/Scale/Koltin -> Reactive/Stream
        Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) // 0-9 集合
                .filter(v -> v % 2 == 1) // 判断数值->获取奇数
                .map(v -> v - 1) // 奇数变偶数
                .reduce(Integer::sum) // 聚合操作
                .ifPresent(System.out::println) // 输出 0 + 2 + 4 + 6 + 8
Stream 是 Iterator 模式，数据已完全准备，拉模式（Pull）

Reactive 是观察者模式，来一个算一个，推模式（Push），当有数据变化的时候，作出反应（Reactor）

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



#### Kuzz]()