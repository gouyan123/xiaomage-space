# 第九节 Spring Cloud 负载均衡
> ```
> JSR 305 meta-annotations：注解做编译约束；例如，@Nullable表示允许为空；
> ```
## 主要内容
### RestTemplate 原理与扩展；目的：将 java对象 转换为 自定义类型，不一定非要转换为 json或者 xml类型；
Spring 核心 HTTP消息转换器 `HttpMessageConverter`
REST 有自描述消息，其中包括 媒体类型(`MediaType`)：text/html;text/xml;application/json；为什么需要 媒体类型描述呢？因为 Http协议特点：http传输的是纯文本，需要自我描述，否则无法区分；
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
		this.messageConverters.add(new ByteArrayHttpMessageConverter());        //http文本协议 转换为 字节数组
		this.messageConverters.add(new StringHttpMessageConverter());           //http文本协议 转换为 字符串
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
微服务要使用轻量级的协议，例如REST，Spring遵从该原则，使用`RestTemplate`作为Spring Cloud的核心的调用器；
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
#### 负载均衡器
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

自定义 负载均衡：
代码见：microservices-project/spring-cloud-project/spring-cloud-client-application项目；
创建 com.gupao.micro.services.spring.cloud.client.controller.ClientController类，controller类只负责调用 restTemplate.getForObject()，不管负载均衡
restTemplate里面负责 负载均衡，restTemplate怎么实现负载均衡？
RestTemplate extends InterceptingHttpAccessor，RestTemplate继承InterceptingHttpAccessor的 getInterceptors()方法，代码如下：
```java
public abstract class InterceptingHttpAccessor extends HttpAccessor {
	private final List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
	public List<ClientHttpRequestInterceptor> getInterceptors() {
        return this.interceptors;
    }
    public void setInterceptors(List<ClientHttpRequestInterceptor> interceptors) {
    		// Take getInterceptors() List as-is when passed in here
    		if (this.interceptors != interceptors) {
    			this.interceptors.clear();
    			this.interceptors.addAll(interceptors);
    			AnnotationAwareOrderComparator.sort(this.interceptors);
    		}
    	}
}
```
RestTemplate实现负载均衡核心思想：
restTemplate.getForObject("http://spring.application.name/hi")，在eureka注册中心，一个服务名下面有多个实例，选择服务名称对应的 多个实例中的一个，
取出其 ip，port，替代 服务名spring.application.name，然后发送http请求；

RestTemplate服务调用器 与 拦截器ClientHttpRequestInterceptor关系？将拦截器设置到RestTemplate中，当RestTemplate调用服务时，首先会调用拦截器的
intercepter()方法，再调用RestTemplate.getForObject("http://...")方法
spring-cloud-client-application项目中创建 拦截器 LoadBalancedRequestInterceptor类，实现ClientHttpRequestInterceptor接口，拦截器可以参考官方的
LoadBalancerInterceptor；
在ClientController类中，将 拦截器LoadBalancedRequestInterceptor设置到 RestTemplate中，代码如下：
```java
public class ClientController {
    @Bean
    @Autowired
    public RestTemplate restTemplate(ClientHttpRequestInterceptor interceptor){
        Template template = new Template();
        /**restTemplate添加拦截器*/
        restTemplate.setInterceptors(Arrays.asList(interceptor));
        return restTemplate;
    }
}
```

自定义注解 com.gupao.micro.services.spring.cloud.client.annotation.CustomizedLoadBalanced，利用注解实现过滤如下，从IOC容器中选加了@CustomizedLoadBalanced注解的RestTemplate类的实例对象
public Object customizer(@CustomizedLoadBalanced Collection<RestTemplate> restTemplates,ClientHttpRequestInterceptor interceptor)

测试：
1、开启spring-cloud-server-application服务，开启spring-cloud-client-application服务
2、请求 http://localhost:8888/invoke/spring-cloud-server-application/say?message=world：访问spring-cloud-client-application服务的ClientController类的
/invoke/spring-cloud-server-application/say绑定的方法invokeSay()，invokeSay()方法里面通过 restTemplate.getForObject()调用spring-cloud-server-application
服务的 服务uri /say
3、发送请求后，会被 LoadBalancedRequestInterceptor implements ClientHttpRequestInterceptor拦截器的 interceptor()方法拦截




