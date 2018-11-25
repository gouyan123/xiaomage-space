# 第九节 Spring Cloud 负载均衡



> ```
> JSR 305 meta-annotations
> 注解做编译约束
> ```



## 主要内容



### RestTemplate 原理与扩展



Spring 核心 HTTP 消息转换器 `HttpMessageConverter`

REST 自描述消息：媒体类型（`MediaType`）， text/html;text/xml;application/json

HTTP 协议特点：纯文本协议，自我描述



* REST 服务端

* REST 客户端

  反序列化：文本（通讯） -> 对象（程序使用）

  序列化：对象 -> 文本



####  `HttpMessageConverter` 分析



##### 判断是否可读可写



```java
public interface HttpMessageConverter<T> {

	boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);

	
	boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);
}
```

clazz = Person.class



##### 当前支持的媒体类型

```java
public interface HttpMessageConverter<T> {	
	List<MediaType> getSupportedMediaTypes();
}
```

`MappingJackson2HttpMessageConverter`



##### 反序列化



```java
public interface HttpMessageConverter<T> {	
	T read(Class<? extends T> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException;
}
```

特别提醒：Spring Web MVC  依赖 Servlet，Spring 在早期设计时，它就考虑到了去 Servlet 化。

HttpInputMessage 类似于 HttpServletRequest



```java
public interface HttpInputMessage extends HttpMessage {

	InputStream getBody() throws IOException;
    
	// 来自于 HttpMessage
	HttpHeaders getHeaders();

}
```



类比 `HttpServletRequest`

```java
public interface HttpServletRequest {    
    
    // 来自于 ServletRequest
	public ServletInputStream getInputStream() throws IOException;
    
    public Enumeration<String> getHeaders(String name);
 
    public Enumeration<String> getHeaderNames();
}
```



`RestTemplate `利用 `HttpMessageConverter` 对一定媒体类型序列化和反序列化

JSON

XML

TEXT



它不依赖于 Servlet API，它自定义实现

对于服务端而言，将 Servlet API 适配成 `HttpInputMessage` 以及 `HttpOutputMessage`



`RestTemplate ` 对应多个 `HttpMessageConverter`，那么如何决策正确媒体类型。



#### RestTemplate 在 `HttpMessageConverter` 设计



```java
public class RestTemplate extends InterceptingHttpAccessor implements RestOperations {
    ...
    // List 形式
    private final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    ...
    public RestTemplate() {
		this.messageConverters.add(new ByteArrayHttpMessageConverter());
		this.messageConverters.add(new StringHttpMessageConverter());
		this.messageConverters.add(new ResourceHttpMessageConverter(false));
		this.messageConverters.add(new SourceHttpMessageConverter<>());
		this.messageConverters.add(new AllEncompassingFormHttpMessageConverter());

		if (romePresent) {
			this.messageConverters.add(new AtomFeedHttpMessageConverter());
			this.messageConverters.add(new RssChannelHttpMessageConverter());
		}

		if (jackson2XmlPresent) {
			this.messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
		}
		else if (jaxb2Present) {
			this.messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
		}

		if (jackson2Present) {
			this.messageConverters.add(new MappingJackson2HttpMessageConverter());
		}
		else if (gsonPresent) {
			this.messageConverters.add(new GsonHttpMessageConverter());
		}
		else if (jsonbPresent) {
			this.messageConverters.add(new JsonbHttpMessageConverter());
		}

		if (jackson2SmilePresent) {
			this.messageConverters.add(new MappingJackson2SmileHttpMessageConverter());
		}
		if (jackson2CborPresent) {
			this.messageConverters.add(new MappingJackson2CborHttpMessageConverter());
		}
	}
}
```



* 添加内建 `HttpMessageConvertor` 实现
* 有条件地添加第三方库`HttpMessageConvertor` 整合实现



> 问题场景一： http://localhost:8080/person -> XML 而不是 Jackson
>
> Postman 、`curl` 场景最为明显
>
> 没有传递请求头，无从选择媒体类型
>
> 假设 Person 既能被 XML 读取，有能被 JSON 读取



> Content-Type: text/html; charset=utf-8



#### RestTemplate 扩展



##### 扩展 HTTP 客户端

* ClientHttpRequestFactory
  * Spring 实现
    * SimpleClientHttpRequestFactory
  * HttpClient
    * HttpComponentsClientHttpRequestFactory
  * OkHttp
    * OkHttp3ClientHttpRequestFactory
    * OkHttpClientHttpRequestFactory



微服务要使用轻量级的协议，比如 REST

Spring Cloud `RestTemplate` 核心的调用器



企业整合模型（叫好，不叫座） -> Spring Integration

DDD



#### `RestTemplate` 整合 Zookeeper









### Netflix Ribbon

`@LoadBalanced` 利用注解来过滤，注入方和声明方同时使用





#### 负载均衡客户端

`ServiceInstanceChooser`

`LoadBalancerClient`



#### 负载均衡上下文

`LoadBalancerContext`





#### 负载均衡规则

`ILoadBalancer`





Q: @Qualifier为什么选择了自定义的RestTemplate而不是lbRestTemplate呢？是按照先后set的吗 

`@Qualifier` “父”注解，`@Qualifier` , `@LoadBalanced`



@Autowired

Collection<Object>  



List<Object>



Set<Object>



## 下节预习

### 回顾去年 VIP

[第五节 Spring Cloud Hystrix](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-5)



