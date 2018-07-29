# 第五节 Spring WebFlux 运用





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