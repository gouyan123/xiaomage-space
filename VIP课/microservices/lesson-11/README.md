# 第十一节 Spring Cloud 服务调用



## 预备知识





## 分析服务调用引入背景



`@LoadBalanced` `RestTemplate` 限制

* 面向 URL 组件，必须依赖于 主机+端口 + URI
* 并非接口编程（Spring Cloud中，需要理解应用名称+ 服务 URI）

`RestTemplate` 不依赖于服务接口，仅关注 REST 响应内容。



举例：

> ```java
> lbRestTemplate.getForObject("http://" + serviceName + "/say?message=" + message, String.class);
> ```



## 主要内容

### Spring Cloud Feign 基本用法



#### Spring Cloud Feign 客户端注解 `@FeignClient`

服务（应用）定位

> @FeignClient("${service.name}") // 服务提供方的应用名称



服务 URI 定位

> 注意：Spring Cloud Feign 和 OpenFeign 区别



##### REST 服务端框架纵向比较



Spring Cloud Feign 是 OpenFeign 扩展，并且使用 Spring MVC 注解来做 URI 映射，比如 `@RequestMapping` 或 `@GetMapping` 之类

OpenFeign：灵感来自于 JAX-RS（Java REST 标准），重复发明轮子。

JAX-RS：[Java REST 标准](https://github.com/mercyblitz/jsr/tree/master/REST)，可移植性高，Jersey（Servlet 容器）、Weblogic

> JSR 参考链接：https://github.com/mercyblitz/jsr



| 技术栈             | HTTP 方法              | 变量路径        | 请求参数        | 自描述消息                                            | 内容协商 |
| ------------------ | ---------------------- | --------------- | --------------- | ----------------------------------------------------- | -------- |
| JAX-RS             | `@GET`                 | `@PathParam`    | `@FormParam`    | @Produces("application/widgets+xml")                  |          |
| Spring Web MVC     | `@GetMapping`          | `@PathVariable` | `@RequestParam` | `@RequestMapping(produces="application/widgets+xml")` |          |
| OpenFeign          | @RequestLine("GET...") | `@Param`        | `@Param`        |                                                       |          |
| Spring Cloud Feign | `@GetMapping`          | `@PathVariable` | `@RequestParam` |                                                       |          |





> zuul IP:port/$service_name/$uri



#### REST 核心概念（Java 技术描述）



##### 请求映射

`@RequestMapping`



##### 请求参数处理

`@RequestParam`



##### 请求主题处理

`@RequestBody`



##### 响应处理

`@ResponseBody`

`@ResponseStatus`

`@ResponseBody` + `@ResponseStatus` <= `ResponseEntity`



##### 自描述消息

`@RequestMapping(produces="application/widgets+xml")`



##### 内容协商

`ContentNegotiationManager`

理论知识：https://developer.mozilla.org/en-US/docs/Web/HTTP/Content_negotiation



#### 整合 Spring Cloud Feign



##### 增加 Spring Cloud Feign 依赖

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
```



##### 整合 `@EnableFeignClients`



```java
@SpringBootApplication // 标准 Spring Boot 应用
@EnableDiscoveryClient // 激活服务发现客户端
@EnableScheduling
@EnableFeignClients
public class SpringCloudClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
```



##### 整合 `@FeignClient`

之前实现

```java
    @GetMapping("/lb/invoke/{serviceName}/say") // -> /say
    public String lbInvokeSay(@PathVariable String serviceName,
                              @RequestParam String message) {
        // Ribbon RestTemplate 发送请求到服务器
        // 输出响应
        return lbRestTemplate.getForObject("http://" + serviceName + "/say?message=" + message, String.class);
    }
```

整合 `@FeignClient` 实现

```java
@FeignClient(name = "spring-cloud-server-application")
public interface SayingService {

    @GetMapping("/say")
    String say(@RequestParam("message") String message);

}
```



注入 `SayingService`

```java
    @Autowired
    private SayingService sayingService;
```

调用 `SayingService`

```java
    @GetMapping("/feign/say")
    public String feignSay(@RequestParam String message) {
        return sayingService.say(message);
    }
```

启动 ZK 服务器

启动 `spring-cloud-server-application`

启动 `spring-cloud-client-application`



#### 实现自定义 RestClient（模拟 `@FeignClient`）



##### Spring Cloud Feign 编程模型特征

* `@Enable` 模块驱动
* `@*Client` 绑定客户端接口，指定应用名称
* 客户端接口指定请求映射 `@RequetMapping`
* 客户端接口指定请求参数 `@RequetParam`
  * 必须指定 `@RequestParam#value()`
* `@Autowired` 客户端接口是一个代理



##### 实现 `@RestClient `

```java
/**
 * Rest Client 注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestClient {

    /**
     * REST 服务应用名称
     * @return
     */
    String name();
}
```



##### 实现 `@RestClient` 服务接口

```java
@RestClient(name = "spring-cloud-server-application")
public interface SayingRestService {

    @GetMapping("/say")
    String say(@RequestParam("message") String message);

}
```





##### 实现 `@Enable` 模块

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RestClientsRegistrar.class)
public @interface EnableRestClient {

    /**
     * 指定 @RestClient 接口
     * @return
     */
    Class<?>[] clients() default {};
}
```



##### 实现 `RestClientsRegistrar`

- 指定 `@RestClient` 服务接口
  - 识别 `@RestClient`
  - 过滤所有 `@RequestMapping` 方法
- 将 `@RestClient` 服务接口注册 Bean
  - `@RestClient` 服务接口形成代理实现
    - `say` 方法执行 REST 请求





## 下节预习

### 公开课《微服务网关新选择 Spring Cloud Gateway》

### 去年 VIP Spring Cloud [第7节 Spring Cloud Zuul](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-7)



