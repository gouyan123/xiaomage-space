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
* `@EnableXxx`装配：`@EnableZuulProxy`，配合注解：`@Import`，将依赖包里面的 配置bean 实例化到 当前IOC容器；
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
创建 GatewayServlet类进行路由，启动spring-cloud-server-application和spring-cloud-client-application
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





### 整合负载均衡(Ribbon)

#### [官方实现](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/single/spring-cloud.html#_ribbon_with_zookeeper)



#### 实现 `ILoadBalancer`



#### 实现 `IRule`







## 下节预习

### 去年 VIP Spring Cloud [第八节 Spring Cloud Stream (上)](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-8)

### 去年 VIP Spring Cloud [第九节 Spring Cloud Stream (下)](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-9)





