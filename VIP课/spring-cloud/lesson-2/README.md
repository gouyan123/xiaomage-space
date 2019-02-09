-----
ppt内容：
主要议题：介绍 Environment仓储，Spring Cloud分布式配置，动态配置属性 Bean，健康指标；
1、介绍 Environment仓储：
①{application}：配置使用客户端应用名称；
②{profile}：客户端 spring.profiles.active配置；
③{label}：服务端配置文件版本标识；
2、Spring Cloud分布式配置：
①config服务端配置
spring.cloud.config.server.git.uri
spring.cloud.config.server.git.*
②config客户端配置
spring.cloud.config.uri
spring.cloud.config.name
spring.cloud.config.profile
spring.cloud.config.label
3、动态配置属性 Bean：
@RefreshScope，RefreshEndpoint，ContextRefresher；
4、健康检查：
/health，HealthEndpoint，HealthIndicator；

-----
# Spring Cloud Config Server



## 构建 Spring Cloud 配置服务器

### 实现步骤

1. 在 Configuration Class 标记`@EnableConfigServer`

2. 配置文件目录（基于 git）

   1.  gupao.properties （默认） // 默认环境，跟着代码仓库

   2.  gupao-dev.properties ( profile = "dev") // 开发环境

   3.  gupao-test.properties ( profile = "test") // 测试环境

   4.  gupao-staging.properties ( profile = "staging") // 预发布环境

   5.  gupao-prod.properties ( profile =  "prod") // 生产环境

      ```
      十月 25 20:46 gupao.properties
      十月 25 20:52 gupao-dev.properties
      十月 25 20:53 gupao-prod.properties
      十月 25 20:52 gupao-staging.properties
      十月 25 20:47 gupao-test.properties
      ```

3.  服务端配置配置版本仓库（本地）

   ```properties
   spring.cloud.config.server.git.uri = \
     file:///E:/Google%20Driver/Private/Lessons/gupao/xiaomage-space/
   ```

> 注意：放在存有`.git`的根目录

完整的配置项：

```properties
### 配置服务器配置项
spring.application.name = config-server
### 定义HTTP服务端口
server.port = 9090
### 本地仓库的GIT URI 配置
spring.cloud.config.server.git.uri = \
  file:///E:/Google%20Driver/Private/Lessons/gupao/xiaomage-space/

### 全局关闭 Actuator 安全
# management.security.enabled = false
### 细粒度的开放 Actuator Endpoints
### sensitive 关注是敏感性，安全
endpoints.env.sensitive = false
```



## 构建 Spring Cloud 配置客户端

### 实现步骤

1.  创建`bootstrap.properties` 或者 `bootstrap.yml`文件

2. `bootstrap.properties` 或者 `bootstrap.yml`文件中配置客户端信息

   ```properties
   ### bootstrap 上下文配置
   # 配置服务器 URI
   spring.cloud.config.uri = http://localhost:9090/
   # 配置客户端应用名称:{application}
   spring.cloud.config.name = gupao
   # profile 是激活配置
   spring.cloud.config.profile = prod
   # label 在Git中指的分支名称
   spring.cloud.config.label = master 
   ```

3. 设置关键 Endpoints 的敏感性

   ```proper
   ### 配置客户端配置项
   spring.application.name = config-client

   ### 全局关闭 Actuator 安全
   management.security.enabled = false
   ### 细粒度的开放 Actuator Endpoints
   ### sensitive 关注是敏感性，安全
   endpoints.env.sensitive = false
   endpoints.refresh.sensitive = false
   endpoints.beans.sensitive = false
   endpoints.health.sensitive = false
   endpoints.actuator.sensitive = false
   ```



## @RefreshScope 用法

```java
@RestController
@RefreshScope
public class EchoController {

    @Value("${my.name}")
    private String myName;

    @GetMapping("/my-name")
    public String getName(){
        return myName;
    }

}
```



通过调用`/refresh` Endpoint 控制客户端配置更新



### 实现定时更新客户端

```java
@Scheduled(fixedRate = 5 * 1000, initialDelay = 3 * 1000)
	public void autoRefresh() {

		Set<String> updatedPropertyNames = contextRefresher.refresh();

		updatedPropertyNames.forEach( propertyName ->
				System.err.printf("[Thread :%s] 当前配置已更新，具体 Key：%s , Value : %s \n",
				Thread.currentThread().getName(),
				propertyName,
				environment.getProperty(propertyName)
				));
	}
```





## 健康检查



### 意义

比如应用可以任意地输出业务健康、系统健康等指标



端点URI：`/health`

实现类：`HealthEndpoint`

健康指示器：`HealthIndicator`，

`HealthEndpoint`：`HealthIndicator` ，一对多



### 自定义实现`HealthIndicator`

1. 实现`AbstractHealthIndicator`

   ```java
   public class MyHealthIndicator extends AbstractHealthIndicator {

       @Override
       protected void doHealthCheck(Health.Builder builder)
               throws Exception {
           builder.up().withDetail("MyHealthIndicator","Day Day Up");
       }
   }
   ```

   ​

2.  暴露 `MyHealthIndicator` 为 `Bean`

   ```java
   @Bean
   public MyHealthIndicator myHealthIndicator(){
     return new MyHealthIndicator();
   }	
   ```

3. 关闭安全控制

   ```properties
   management.security.enabled = false
   ```

   ​



#### 其他内容



REST API = /users , /withdraw

HATEOAS =  REST 服务器发现的入口，类似 UDDI (Universal Description Discovery and Integration)

HAL

/users
/withdraw
...



Spring Boot 激活 `actuator` 需要增加 Hateoas 的依赖：

```xml
<dependency>
  <groupId>org.springframework.hateoas</groupId>
  <artifactId>spring-hateoas</artifactId>
</dependency>
```

以客户端为例：

```json
{
    "links": [{
        "rel": "self",
        "href": "http://localhost:8080/actuator"
    }, {
        "rel": "heapdump",
        "href": "http://localhost:8080/heapdump"
    }, {
        "rel": "beans",
        "href": "http://localhost:8080/beans"
    }, {
        "rel": "resume",
        "href": "http://localhost:8080/resume"
    }, {
        "rel": "autoconfig",
        "href": "http://localhost:8080/autoconfig"
    }, {
        "rel": "refresh",
        "href": "http://localhost:8080/refresh"
    }, {
        "rel": "env",
        "href": "http://localhost:8080/env"
    }, {
        "rel": "auditevents",
        "href": "http://localhost:8080/auditevents"
    }, {
        "rel": "mappings",
        "href": "http://localhost:8080/mappings"
    }, {
        "rel": "info",
        "href": "http://localhost:8080/info"
    }, {
        "rel": "dump",
        "href": "http://localhost:8080/dump"
    }, {
        "rel": "loggers",
        "href": "http://localhost:8080/loggers"
    }, {
        "rel": "restart",
        "href": "http://localhost:8080/restart"
    }, {
        "rel": "metrics",
        "href": "http://localhost:8080/metrics"
    }, {
        "rel": "health",
        "href": "http://localhost:8080/health"
    }, {
        "rel": "configprops",
        "href": "http://localhost:8080/configprops"
    }, {
        "rel": "pause",
        "href": "http://localhost:8080/pause"
    }, {
        "rel": "features",
        "href": "http://localhost:8080/features"
    }, {
        "rel": "trace",
        "href": "http://localhost:8080/trace"
    }]
}
```



## 问答

1. 小马哥，你们服务是基于啥原因采用的springboot 的， 这么多稳定性的问题？

   答：Spring Boot 业界比较稳定的微服务中间件，不过它使用是易学难精！

2. 小马哥 为什么要把配置项放到 git上，为什么不放到具体服务的的程序里边 ；git在这里扮演什么样的角色 ;是不是和 zookeeper 一样

   答：Git 文件存储方式、分布式的管理系统，Spring Cloud 官方实现基于 Git，它达到的理念和 ZK 一样。

3. 一个DB配置相关的bean用@RefreshScope修饰时，config service修改了db的配置，比如mysql的url，那么这个Bean会不会刷新？如果刷新了是不是获取新的连接的时候url就变了？

   如果发生了配置变更，我的解决方案是重启 Spring Context。@RefreshScope 最佳实践用于 配置Bean，比如：开关、阈值、文案等等

   A B C
   1 1 1

   A* B C
   0  1 1

   A* B* C
   1  0  1

   A* B* C
   1  1  0

   A* B* C*
   1  1  1

4. 如果这样是不是动态刷新就没啥用了吧

   答：不能一概而论，@RefreshScope 开关、阈值、文案等等场景使用比较多