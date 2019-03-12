# 第九节 Spring Cloud 负载均衡
> ```
> JSR 305 meta-annotations：注解做编译约束；例如，@Nullable表示允许为空；
> ```
## 主要内容
### RestTemplate 原理与扩展；目的：将 java对象 转换为 自定义类型，不一定非要转换为 json或者 xml类型；
Spring 核心 HTTP消息转换器 `HttpMessageConverter`
REST 有自描述消息，其中包括 媒体类型(`MediaType`)：text/html;text/xml;application/json；为什么需要 媒体类型描述呢？因为 Http协议特点，具体如下：
HTTP 协议特点：传输的是纯文本，需要自我描述，否则无法区分；
REST分为2个端：服务端，客户端；
* REST 服务端：要将 文本 转换成 对象[反序列化]，或者将 对象 转换为 文本[序列化]；
* REST 客户端：要将 文本 转换成 对象[反序列化]，或者将 对象 转换为 文本[序列化]；
其中，文本给通讯使用，对象给程序使用；
####  `HttpMessageConverter` 分析：这是 REST的 HTTP消息转换接口
```java
public interface HttpMessageConverter<T> {
    //判断是否可读，即可反序列化；clazz表示 对象类型，
	boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);
	//判断是否可写，即可序列化
	boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);
	//获取当前支持的媒体类型
	List<MediaType> getSupportedMediaTypes();
	//反序列化
	T read(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException;
	//序列化
	void write(T t, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException;
}
```
特别提醒：Spring Web MVC依赖 Servlet，Spring 在早期设计时，它就考虑到了去 Servlet 化；HttpInputMessage 类似于 HttpServletRequest；
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
`RestTemplate `利用 `HttpMessageConverter` 对一定 媒体类型[JSON,XML,TEXT] 进行序列化和反序列化，它不依赖于 Servlet API，它自定义实现，对于 REST服务端
而言，将 Servlet API 适配成 `HttpInputMessage` 以及 `HttpOutputMessage`；
一个`RestTemplate ` 对应多个 `HttpMessageConverter`，那么如何 选择正确的媒体类型？
#### RestTemplate设计：RestTemplate持有 `HttpMessageConverter`集合
```java
public class RestTemplate extends InterceptingHttpAccessor implements RestOperations {
    // List 形式
    private final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    public RestTemplate() {
        //Start 添加内建 `HttpMessageConvertor` 实现
		this.messageConverters.add(new ByteArrayHttpMessageConverter());
		this.messageConverters.add(new StringHttpMessageConverter());
		this.messageConverters.add(new ResourceHttpMessageConverter(false));
		this.messageConverters.add(new SourceHttpMessageConverter<>());
		this.messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        //End 添加内建 `HttpMessageConvertor` 实现
        //Start 有条件地添加第三方库`HttpMessageConvertor` 整合实现
		if (romePresent) { //romePresent：第三方类库存在，就内建，不存在就不内建
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
		//End 有条件地添加第三方库`HttpMessageConvertor` 整合实现
	}
}
```
* 添加内建 `HttpMessageConvertor` 实现
* 有条件地添加第三方库`HttpMessageConvertor` 整合实现
> 问题场景一： Postman或者`curl`命令 请求http://localhost:8080/person，返回XML 而不是 Jackson原因？
> 没有传递请求头，无从选择媒体类型，RestTemplate类中先 内建哪个 http消息转换器，就转换成什么类型；
>
> 假设 Person 既能被 XML 读取，又能被 JSON 读取
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



