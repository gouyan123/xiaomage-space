#  [第三节 REST](lesson-3)



## 主要内容



### REST 理论基础



RPC ( Remote Procedure Call )

* 语言相关
  * Java - RMI（Remote Method Invocation）
  * .NET - COM+、
* 语言无关
  * SOA 
    * Web Services
      * SOAP（传输介质协议）
      * HTTP、SMTP（通讯协议）
  * 微服务（MSA）
    * REST
      * HTML、JSON、XML 等等
      * HTTP（通讯协议）
        * HTTP 1.1
          * 短连接
          * Keep-Alive
          * 连接池
          * Long Polling
        * HTTP/2
          * 长连接
      * 技术
        * Spring 客户端 ： RestTemplate
        * Spring WebMVC ： @RestController = `@Controller` + `@ResponseBody` + `@RequestBody`
        * Spring Cloud : `RestTemplate` 扩展 + `@LoadBalanced`





[REST](https://en.wikipedia.org/wiki/Representational_state_transfer)（英文）

#### Cacheability（可缓存性）



`@ResponseBody` -> 响应体（Response Body）

* 响应（Response）

  * 响应头（Headers）

    * 请求方法

      * HEAD

    * 元信息（Meta-Data）

      * Accept-Language -> `Locale`
      * Connection -> Keep-Alive

    * 实现

      多值 Map `MultiValueMap`

      Key : Value = 1 : N

      Name : Value = 1 : N

      ```java
      public class HttpHeaders implements MultiValueMap<String, String>, Serializable {
          ...
      }
      ```

      

  * 响应体

    * 业务信息（Business Data）
    * Body：HTTP 实体、REST
      * `@ResponseBody`
      * `HttpEntity.body` 属性（泛型结构）
    * Payload ： 消息 JMS、事件、SOAP

  ```java
  public class HttpEntity<T> {
  	...
  	private final HttpHeaders headers;
  
  	@Nullable
  	private final T body;
  }
  ```

  





#### HTTP 状态码（`org.springframework.http.HttpStatus`）

* 200
  * `org.springframework.http.HttpStatus#OK`
* 304
  * `org.springframework.http.HttpStatus#NOT_MODIFIED`
  * 第一次完整请求，获取响应头（200），直接获取
  * 第二次请求，只读取头信息，响应头（304），客户端（流量器）取上次 Body 结果
* 400
  * `org.springframework.http.HttpStatus#BAD_REQUEST`
* 404
* 500



#### Uniform interface（统一接口）



##### 资源定位 - URI



##### 资源操作 - HTTP 动词

GET

* `@GetMapping`

  * 注解属性别名和覆盖（https://github.com/spring-projects/spring-framework/wiki/Spring-Annotation-Programming-Model#attribute-aliases-and-overrides）

    * Spring Framework 4.2 引入
      * Spring Boot 1.3 才可以使用
    * Spring Boot 加以发展

    ```java
    @RequestMapping(method = RequestMethod.POST) // 注解“派生性”
    public @interface PostMapping {
        ...
        @AliasFor(annotation = RequestMapping.class) // 注解别名
    	String name() default "";
        ...
    }
    ```

    `@PostMapping` 是注解，`@RequestMapping` 是 `@PostMapping`  的注解：

    * `@RequestMapping`  是  `@PostMapping` 的元注解
    * `@RequestMapping` 元标注了  `@PostMapping`

    `@AliasFor` 只能标注在目标注解的属性，所`annotation() `的注解必须是元注解，该注解 `attribute()` 必须元注解的属性

PUT

* `@PutMapping`

POST

* `@PostMapping`

PATCH

* `@PatchMapping`

* 限制

  * Servlet API 没有规定 PATCH 

  * Spring Web 对其做了扩展

    ```java
    public abstract class FrameworkServlet extends HttpServletBean implements ApplicationContextAware {
    
        ...
    protected void service(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
    
    		HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
    		if (httpMethod == HttpMethod.PATCH || httpMethod == null) {
    			processRequest(request, response);
    		}
    		else {
    			super.service(request, response);
    		}
    	}
        ...
    }
    ```

DELETE

* `@DeleteMapping`



#### 自描述消息



##### 注解驱动

* `@RequestBody`

  JSON -> `MappingJackson2HttpMessageConverter`

  TEXT -> `StringHttpMessageConverter`

* `@ResponseBody`

  JSON -> `MappingJackson2HttpMessageConverter`

  TEXT -> `StringHttpMessageConverter`

返回值处理类：`RequestResponseBodyMethodProcessor`



##### 接口编程

`ResponseEntity` extends `HttpEntity`

`RequestEntity` extends `HttpEntity`

返回值处理类：`HttpEntityMethodProcessor`



##### 媒体类型（`MediaType`）

- `org.springframework.http.MediaType#APPLICATION_JSON_UTF8_VALUE`
  - "application/json;charset=UTF-8"

HTTP 消息转换器（`HttpMessageConverter`）

- application/json
  - `MappingJackson2HttpMessageConverter`
- text/html
  - `StringHttpMessageConverter`

#### 代码导读



`@EnableWebMvc`

* 导入 `DelegatingWebMvcConfiguration`（配置 Class）
  * 注册   `WebMvcConfigurer`
    * 装配各种 Spring MVC 需要的Bean
    * 注解驱动扩展点
      * `HandlerMethodArgumentResolver`
      * `HandlerMethodReturnValueHandler`
      * `@RequestBody` 和 `@ResponseBody` 实现类
        * `RequestResponseBodyMethodProcessor`
        * `HttpEntityMethodProcessor`



实现 `WebMvcConfigurer`？



`WebMvcConfigurerAdapter` 实现















> URI 与 URL
>
> U : Uniform 
>
> R : Resource
>
> I：鉴别 
>
> L : 定位
>
> 
>
> 山东和河南都有一个张三，张三就是URI，具体的河南的张三或者山东的张三就是URL 
>
> 
>
> URI
>
> ```
> URI = scheme:[//authority]path[?query][#fragment]
> ```
>
> scheme : HTTP、wechat
>
> URL
>
> protocol 协议
>
> 



###  REST 服务端实践





### REST 客户端实践







### 问答环节

https://ask.gupaoedu.com/questions



Q: 前后端分离和服务端渲染的区别

A: 服务端渲染，数据计算 + HTML 渲染均有服务端完成。 

前后端：数据计算有服务端提供，JSON Web 端点（REST Web 接口），HTML 主要前端 JS 完成，以

React、Vue.js（前端模板引擎）





## 相关视频



### 去年 VIP 课

#### Spring Boot

* Spring Web MVC（上）
* Spring Web MVC（下）





## 下节课程预习内容

### 理论

* Reactive 原理
* WebFlux 使用场景
* WebFlux 整体架构











