# 第十二节 Spring Cloud Gateway
### 上周六的问题
接口方法参数名称在 `ParameterNameDiscoverer ` 找不到
类方法参数名称在 `ParameterNameDiscoverer` 可以找到
javac -g  编译时带有debug 信息
javac -g:none 编译时不带有debug信息

## Spring Cloud Gateway
与Spring WebFlux类似；目的：去 Servlet 化（Java EE Web 技术中心）；技术：Reactor + Netty + Lambda
最新技术：Spring Cloud Function

### 小马哥 Java 语言技术预判
1、函数式编程（Java Lambda、Koltin、Scala、Groovy）
2、网络编程（Old Java BIO、Java 1.4 NIO( 就是Reactor 反应堆模式)、Java 1.7 NIO2 和 AIO、Netty）
3、Reactive：编程模型（非阻塞 + 异步） + 对象设计模式（观察者模式）

典型的技术代表：
* 单机版（函数式、并发编程）
  * Reactor
  * RxJava
  * Java 9 Flow API
* 网络版（函数式、并发编程、网络编程）
  * Netty + Reactor 延伸出 WebFlux、Spring Cloud Gateway
  * Vert.x （Netty）
## Netty很重要；
## Gateway 主要内容
### Gateway 取代 Zuul 1.x，Zuul1.x是基于 Servlet的；
Resin Servlet容器号称可以与Nginx匹敌
Tomcat Servlet容器的连接器：Java Blocking Connector；Java Non Blocking Connector；APR/native Connector
其他Servlet容器还有 JBoss，Weblogic；

Zuul编程模型：一个框架会有一个限定的范围，Zuul是 Netflix自己实现的，实现的API不是非常友好；

Zuul 实现原理：
* `@EnableXxx`装配模块：`@EnableZuulProxy`，配合注解：`@Import`，将依赖包里面的 配置bean 实例化到 当前IOC容器；
* 依赖服务发现：Registration；
  * 我是谁
  * 目的服务在哪里
* 依赖服务路由：ServiceRouteMapper；
  * URI 映射到目的服务
* 依赖服务熔断（可选）
### 整合服务发现，整合负载均衡，举例说明
假设 URI : `/gateway/spring-cloud-server-application/say`
其中 Servlet Path ：`/gateway`
`spring-cloud-server-application` 是服务的应用名称
`/say` 是 `spring-cloud-server-application` 的服务 URI

创建 microservices-project/spring-cloud-project/spring-cloud-servlet-gateway项目：
创建 GatewayServlet，利用Servlet实现路由，没有负载均衡，启动spring-cloud-server-application和spring-cloud-client-application
通过spring-cloud-servlet-gateway网关访问spring-cloud-server-application：http://localhost:20000/gateway/spring-cloud-server-application/say?message=world
```java
/**
 * 服务网关的路由规则
 * /{service-name}/{service-uri}
 * /gateway/rest-api/hello-world-> http://127.0.0.1:8080/hello-world
 */
@WebServlet(name = "gateway", urlPatterns = "/gateway/*")
public class GatewayServlet extends HttpServlet {

    @Autowired
    private DiscoveryClient discoveryClient;

    private ServiceInstance randomChoose(String serviceName) {
        // 获取服务实例列表（服务IP、端口、是否为HTTPS）
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
        // 获得服务实例总数
        int size = serviceInstances.size();
        // 随机获取数组下标
        int index = new Random().nextInt(size);
        return serviceInstances.get(index);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ${service-name}/${service-uri}
        String pathInfo = request.getPathInfo();
        String[] parts = StringUtils.split(pathInfo.substring(1), "/");
        // 获取服务名称
        String serviceName = parts[0];
        // 获取服务 URI
        String serviceURI = "/" + parts[1];
        // 随机选择一台服务实例
        ServiceInstance serviceInstance = randomChoose(serviceName);
        // 构建目标服务 URL -> scheme://ip:port/serviceURI
        String targetURL = buildTargetURL(serviceInstance, serviceURI, request);

        // 创建转发客户端
        RestTemplate restTemplate = new RestTemplate();

        // 构造 Request 实体
        RequestEntity<byte[]> requestEntity = null;
        try {
            requestEntity = createRequestEntity(request, targetURL);
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(requestEntity, byte[].class);
            writeHeaders(responseEntity, response);
            writeBody(responseEntity, response);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String buildTargetURL(ServiceInstance serviceInstance, String serviceURI, HttpServletRequest request) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(serviceInstance.isSecure() ? "https://" : "http://")
                .append(serviceInstance.getHost()).append(":").append(serviceInstance.getPort())
                .append(serviceURI);
        String queryString = request.getQueryString();
        if (StringUtils.hasText(queryString)) {
            urlBuilder.append("?").append(queryString);
        }
        return urlBuilder.toString();
    }

    private RequestEntity<byte[]> createRequestEntity(HttpServletRequest request, String url) throws URISyntaxException, IOException {
        // 获取当前请求方法
        String method = request.getMethod();
        // 装换 HttpMethod
        HttpMethod httpMethod = HttpMethod.resolve(method);
        byte[] body = createRequestBody(request);
        MultiValueMap<String, String> headers = createRequestHeaders(request);
        RequestEntity<byte[]> requestEntity = new RequestEntity<byte[]>(body, headers, httpMethod, new URI(url));
        return requestEntity;
    }

    private MultiValueMap<String, String> createRequestHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        List<String> headerNames = Collections.list(request.getHeaderNames());
        for (String headerName : headerNames) {
            List<String> headerValues = Collections.list(request.getHeaders(headerName));
            for (String headerValue : headerValues) {
                headers.add(headerName, headerValue);
            }
        }
        return headers;
    }

    private byte[] createRequestBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }


    /**
     * 输出 Body 部分
     *
     * @param responseEntity
     * @param response
     * @throws 
     */
    private void writeBody(ResponseEntity<byte[]> responseEntity, HttpServletResponse response) throws IOException {
        if (responseEntity.hasBody()) {
            byte[] body = responseEntity.getBody();
            // 输出二进值
            ServletOutputStream outputStream = response.getOutputStream();
            // 输出 ServletOutputStream
            outputStream.write(body);
            outputStream.flush();
        }
    }

    private void writeHeaders(ResponseEntity<byte[]> responseEntity, HttpServletResponse response) {
        // 获取相应头
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        // 输出转发 Response 头
        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            String headerName = entry.getKey();
            List<String> headerValues = entry.getValue();
            for (String headerValue : headerValues) {
                response.addHeader(headerName, headerValue);
            }
        }
    }

}
```

### 整合负载均衡(Ribbon)分析
RestTemplate增加一个LoadBalancerInterceptor，调用Netflix中的LoadBalancer实现，选择服务名下面的一个 ip:port；
跟LoadBalancerInterceptor，该类拦截http请求，将request对象修改后，再调后面service(req,resp)方法，代码如下：
```java
public class LoadBalancerInterceptor implements ClientHttpRequestInterceptor {
    /**跟*/
	private LoadBalancerClient loadBalancer;
    //
}
```
跟 LoadBalancerClient接口，该接口的实现类 RibbonLoadBalancerClient
```java
public interface LoadBalancerClient extends ServiceInstanceChooser {
	<T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException;
}
public class RibbonLoadBalancerClient implements LoadBalancerClient {
    public <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException {
        /**跟*/
        ILoadBalancer loadBalancer = this.getLoadBalancer(serviceId);
        /**看一下 Server类*/
        Server server = this.getServer(loadBalancer);
        if (server == null) {
            throw new IllegalStateException("No instances available for " + serviceId);
        } else {
            RibbonLoadBalancerClient.RibbonServer ribbonServer = new RibbonLoadBalancerClient.RibbonServer(serviceId, server, this.isSecure(server, serviceId), this.serverIntrospector(serviceId).getMetadata(server));
            return this.execute(serviceId, ribbonServer, request);
        }
    }
}
```
跟 ILoadBalancer接口，该接口有很多实现类
```java
public interface ILoadBalancer {
	public void addServers(List<Server> newServers);
	/**一个服务名有多个实例，从中选择一个*/
	public Server chooseServer(Object key);
	public void markServerDown(Server server);
	@Deprecated
	public List<Server> getServerList(boolean availableOnly);
    public List<Server> getReachableServers();
	public List<Server> getAllServers();
}
```
跟chooseServer()的实现类 BaseLoadBalancer#chooseServer()
```java
public class BaseLoadBalancer extends AbstractLoadBalancer implements PrimeConnections.PrimeConnectionListener, IClientConfigAware {
    public Server chooseServer(Object key) {
        if (counter == null) {
            counter = createCounter();
        }
        counter.increment();
        if (rule == null) {
            return null;
        } else {
            try {
                /**rule是IRule接口的默认实现类RoundRobinRule*/
                return rule.choose(key);
            } catch (Exception e) {
                logger.warn("LoadBalancer [{}]:  Error choosing server for key {}", name, key, e);
                return null;
            }
        }
    }
}
```
#### [官方实现](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/single/spring-cloud.html#_ribbon_with_zookeeper)
###自定义负载均衡器：实现 `ILoadBalancer` 实现 `IRule`
spring-cloud-servlet-gateway项目下：
创建ZookeeperLoadBalancer类实现`ILoadBalancer`，直接实现这个顶层接口不太好，可以扩展ILoadBalancer接口的实现类BaseLoadBalancer
创建RibbonGatewayServlet类，持有ZookeeperLoadBalancer类，实现有负载均衡功能的路由，GatewayServlet只有路由功能，没有负载均衡功能
通过spring-cloud-servlet-gateway网关访问spring-cloud-server-application：http://localhost:20000/ribbon/gateway/spring-cloud-server-application/say?message=world
访问spring-cloud-client-application：http://localhost:20000/ribbon/gateway/spring-cloud-client-application/rest/say?message=world
#### 

## 下节预习
### 去年 VIP Spring Cloud [第八节 Spring Cloud Stream (上)](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-8)
### 去年 VIP Spring Cloud [第九节 Spring Cloud Stream (下)](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-9)





