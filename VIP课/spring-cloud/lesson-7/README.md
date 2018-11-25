# Spring Cloud Zuul



## Zuul 基本使用



@EnableEurekaClient



@EnableDiscoveryClient



Nginx + Lua 

Lua：控制规则（A/B Test）

Spring Cloud 学习技巧：

善于定位应用：Feign、Config Server、Eureka、Zuul 、Ribbon定位应用，配置方式是不同



### 增加 @EnableZuulProxy

```java
package com.gupao.springcloudzuuldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class SpringCloudZuulDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudZuulDemoApplication.class, args);
	}

}

```



## 配置路由规则

基本模式：`zuul.routes.${app-name} = /${app-url-prefix}/**`



## 整合 Ribbon

### 启动应用

[`spring-cloud-eureka-server`](spring-cloud-eureka-server-demo)

[`person-service`](spring-cloud-feign-demo/person-service-provider)

### 调用链路

zuul ->  person-service



### 配置方式

```properties
## Zuul 服务端口
server.port = 7070

## Zuul 基本配置模式
# zuul.routes.${app-name}: /${app-url-prefix}/**
## Zuul 配置 person-service 服务调用
zuul.routes.person-service = /person-service/**

## Ribbon 取消 Eureka 整合
ribbon.eureka.enabled = false
## 配置 "person-service" 的负载均衡服务器列表
person-service.ribbon.listOfServers = \
  http://localhost:9090
```

> 注意：http://localhost:7070/person-service/person/find/all
>
> person-service 的 app-url-prefix : /person-service/ 
>
> /person/find/all 是 person-service 具体的 URI



##  整合 Eureka



### 引入 spring-cloud-starter-eureka 依赖

```xml
<!-- 增加 Eureka 客户端的依赖 -->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
```



### 激活服务注册、发现客户端

```java
package com.gupao.springcloudzuuldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class SpringCloudZuulDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudZuulDemoApplication.class, args);
	}

}

```



### 配置服务注册、发现客户端

```properties
## 设置应用名称
spring.application.name = spring-cloud-zuul
## Zuul 服务端口
server.port = 7070

## Zuul 基本配置模式
# zuul.routes.${app-name}: /${app-url-prefix}/**
## Zuul 配置 person-service 服务调用
zuul.routes.person-service = /person-service/**

## 整合 Eureka
## 目标应用的serviceId = person-service
## Eureka Server 服务 URL,用于客户端注册
eureka.client.serviceUrl.defaultZone=\
  http://localhost:12345/eureka


### Ribbon 取消 Eureka 整合
#ribbon.eureka.enabled = false
### 配置 "person-service" 的负载均衡服务器列表
#person-service.ribbon.listOfServers = \
#  http://localhost:9090
```









## 整合 Hystrix



### 服务端提供方：person-service

#### 激活 Hystrix

```java
package com.gupao.spring.cloud.feign.person.service.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * {@PersonService} 提供者应用
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/5
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class PersonServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonServiceProviderApplication.class,args);
    }

}

```

#### 配置 Hystrix 规则



```java
package com.gupao.spring.cloud.feign.person.service.provider.web.controller;

import com.gupao.spring.cloud.feign.api.domain.Person;
import com.gupao.spring.cloud.feign.api.service.PersonService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link PersonService} 提供者控制器（可选实现{@link PersonService}）
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/5
 */
@RestController
public class PersonServiceProviderController {

    private final Map<Long, Person> persons = new ConcurrentHashMap<>();

    private final static Random random = new Random();

    /**
     * 保存
     *
     * @param person {@link Person}
     * @return 如果成功，<code>true</code>
     */
    @PostMapping(value = "/person/save")
    public boolean savePerson(@RequestBody Person person) {
        return persons.put(person.getId(), person) == null;
    }



    /**
     * 查找所有的服务
     *
     * @return
     */
    @GetMapping(value = "/person/find/all")
    @HystrixCommand(fallbackMethod = "fallbackForFindAllPersons",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "100")
            }
    )
    public Collection<Person> findAllPersons() throws Exception {
        // 如果随机时间 大于 100 ，那么触发容错
        int value = random.nextInt(200);
        System.out.println("findAllPersons() costs " + value + " ms.");
        Thread.sleep(value);
        return persons.values();
    }

    /**
     * {@link #findAllPersons()} Fallback 方法
     *
     * @return 返回空集合
     */
    public Collection<Person> fallbackForFindAllPersons() {
        System.err.println("fallbackForFindAllPersons() is invoked!");
        return Collections.emptyList();
    }

}
```



## 整合 Feign

### 服务消费端：person-client



#### 调用链路

spring-cloud-zuul -> person-client -> person-service



#### person-client 注册到 EurekaServer



> 端口信息：
>
> spring-cloud-zuul 端口：7070
>
> person-client 端口：8080
>
> person-service 端口：9090
>
> Eureka Server 端口：12345

启动 [`person-client`](spring-cloud-feign-demo/person-client)

```properties
spring.application.name = person-client

server.port = 8080

## Eureka Server 服务 URL,用于客户端注册
eureka.client.serviceUrl.defaultZone=\
  http://localhost:12345/eureka

management.security.enabled = false
```



### 网关应用：spring-cloud-zuul

#### 增加路由应用到 person-client

```properties
## Zuul 配置 person-client 服务调用
zuul.routes.person-client = /person-client/**
```



#### 测试链路

http://localhost:7070/person-client/person/find/all

spring-cloud-zuul(**7070**) -> person-client(**8080**) -> person-service(**9090**)

等价的 Ribbon（不走注册中心）

```properties
## Ribbon 取消 Eureka 整合
ribbon.eureka.enabled = false
## 配置 "person-service" 的负载均衡服务器列表
person-service.ribbon.listOfServers = \
  http://localhost:9090
## 配置 "person-client" 的负载均衡服务器列表
person-client.ribbon.listOfServers = \
  http://localhost:8080
```





## 整合 Config Server

前面的例子展示 Zuul 、Hystrix、Eureka 以及 Ribbon 能力，可是配置相对是固定，真实线上环境需要一个动态路由，即需要动态配置。



### 配置服务器：spring-cloud-config-server

> 端口信息：
>
> spring-cloud-zuul 端口：7070
>
> person-client 端口：8080
>
> person-service 端口：9090
>
> Eureka Server 端口：12345
>
> Config Server 端口：10000



#### 调整配置项

```properties
### 配置服务器配置项
spring.application.name = spring-cloud-config-server
### 定义HTTP服务端口
server.port = 10000
### 本地仓库的GIT URI 配置
spring.cloud.config.server.git.uri = \
  file:///${user.dir}/src/main/resources/configs

### 全局关闭 Actuator 安全
# management.security.enabled = false
### 细粒度的开放 Actuator Endpoints
### sensitive 关注是敏感性，安全
endpoints.env.sensitive = false
endpoints.health.sensitive = false
```



#### 为  spring-cloud-zuul 增加配置文件

三个 profile 的配置文件：

* zuul.properties
* zuul-test.properties
* zuul-prod.properties



zuul.properties

```properties
## 应用 spring-cloud-zuul 默认配置项（profile 为空)

## Zuul 基本配置模式
# zuul.routes.${app-name}: /${app-url-prefix}/**
## Zuul 配置 person-service 服务调用
zuul.routes.person-service = /person-service/**
```

zuul-test.properties

```properties
## 应用 spring-cloud-zuul 默认配置项（profile == "test")

## Zuul 基本配置模式
# zuul.routes.${app-name}: /${app-url-prefix}/**
## Zuul 配置 person-client 服务调用
zuul.routes.person-client = /person-client/**
```

zuul-prod.properties

```properties
## 应用 spring-cloud-zuul 默认配置项（profile == "prod")

## Zuul 基本配置模式
# zuul.routes.${app-name}: /${app-url-prefix}/**
## Zuul 配置 person-service 服务调用
zuul.routes.person-service = /person-service/**

## Zuul 配置 person-client 服务调用
zuul.routes.person-client = /person-client/**
```



#### 初始化 ${user.dir}/src/main/resources/configs 为 git 根目录

1. 初始化

```
$ git init
Initialized empty Git repository in ${user.dir}/src/main/resources/configs/.git/
```

2. 增加上述三个配置文件到 git 仓库

```
$ git add *.properties
```

3. 提交到本地 git 仓库

```
$ git commit -m "Temp commit"
[master (root-commit) be85bb0] Temp commit
 3 files changed, 21 insertions(+)
 create mode 100644 zuul-prod.properties
 create mode 100644 zuul-test.properties
 create mode 100644 zuul.properties
```

以上操作为了让 Spring Cloud Git 配置服务器实现识别 Git 仓库，否则添加以上三个文件也没有效果。

#### 注册到 Eureka 服务器

##### 增加 spring-cloud-starter-eureka 依赖

```xml
<!-- 增加 Eureka 客户端的依赖 -->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
```

##### 激活服务注册、发现客户端

```java
package com.gupao.springcloudconfigserverdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Set;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class SpringCloudConfigServerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigServerDemoApplication.class, args);
    }

}
```



##### 调整配置项

```properties
## Eureka Server 服务 URL,用于客户端注册
eureka.client.serviceUrl.defaultZone=\
  http://localhost:12345/eureka
```



#### 测试配置

http://localhost:10000/zuul/default

http://localhost:10000/zuul/test

http://localhost:10000/zuul/prod



### 配置网关服务：spring-cloud-zuul

> 端口信息：
>
> spring-cloud-zuul 端口：7070
>
> person-client 端口：8080
>
> person-service 端口：9090
>
> Eureka Server 端口：12345



####  增加 spring-cloud-starter-config 依赖

> 将之前：
>
> zuul.routes.person-service
>
> zuul.routes.person-client
>
> 配置注释

```xml
<!-- 增加 配置客户端的依赖 -->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

#### 创建 bootstrap.properties

#### 配置 config 客户端信息

```properties
## 整合 Eureka
## Eureka Server 服务 URL,用于客户端注册
## application.properties 会继承bootstrap.properties 属性
## 因此，application.properties 没有必要配置 eureka.client.serviceUrl.defaultZone
eureka.client.serviceUrl.defaultZone=\
  http://localhost:12345/eureka

### bootstrap 上下文配置
# 配置客户端应用名称: zuul , 可当前应用是 spring-cloud-zuul
spring.cloud.config.name = zuul
# profile 是激活配置
spring.cloud.config.profile = prod
# label 在Git中指的分支名称
spring.cloud.config.label = master
# 采用 Discovery client 连接方式
## 激活 discovery 连接配置项的方式
spring.cloud.config.discovery.enabled=true
## 配置 config server 应用名称
spring.cloud.config.discovery.serviceId = spring-cloud-config-server
```

#### 测试链路

http://localhost:7070/person-client/person/find/all

spring-cloud-zuul -> person-client -> person-service

http://localhost:7070/person-service/person/find/all

spring-cloud-zuul -> person-service



abc.acme.com -> abc

def.acme.com -> def



需要自定义实现 ZuulFilter

通过 Groovy 来实现动态配置规则









## 问答部分

1. 看下来过程是：通过url去匹配zuul中配置的serviceId然后没整合ribbon时，直接去eureka中找服务实例去调用，如果整合了ribbon时，直接从listofService中取得一个实例，然后调用返回，对不？

   答：大致上可以这么理解，不过对应的listOfServicers 不只是单个实例，而可能是一个集群，主要可以配置域名。

2. 为什么要先调用client而不直接调用server,还是不太理解

   答：这个只是一个演示程序，client 在正式使用场景中，并不是一简单的调用，它可能是一个聚合服务。

3. zuul 是不是更多的作为业务网关

   答：是的，很多企业内部的服务通过 Zuul 做个服务网关

4. 渡劫RequestContext已经存在ThreadLocal中了，为什么还要使用ConcurrentHashMap？

   答：`ThreadLocal `只能管当前线程，不能管理子线程，子线程需要使用`InheritableThreadLocal`。`ConcurrentHashMap` 实现一下，如果上下文处于多线程线程的环境，比如传递到子线程。

   比如：T1 在管理 RequestContext，但是 T1 又创建了多个线程(t1、t2)，这个时候，把上下文传递到了子线程 t1 和 t2 .

   Java 的进程所对应的线程 main 线程（group：main），main 线程是所有子线程的父线程，main线程 T1 ，T1 又可以创建 t1 和 t2 :

   ```java
   @Override
       public Object run() {
           RequestContext ctx = RequestContext.getCurrentContext(); // T1 线程
   	    ServiceExecutor executor = ...;
         executor.submit(new MyRunnable(ctx){
           
           public void run(){
             ctx // t1 线程
           }
           
         });
           return null;
       }
   ```

   ​

   ​

5. ZuulServlet已经管理了RequestContext的生命周期了，为什么ContextLifecycleFilter还要在做一遍？

   答：`ZuulServelt `最终也会清理掉RequestContext:

   ```java
   } finally {
     RequestContext.getCurrentContext().unset();
   }
   ```

   为什么 `ContextLifecycleFilter `也这么干？

   ```java
   } finally {
   RequestContext.getCurrentContext().unset();
   }
   ```

   不要忽略了`ZuulServletFilter`，也有这个处理：

   ```java
   finally {
     RequestContext.getCurrentContext().unset();
   }
   ```

   `RequestContext `是 任何 Servlet 或者 Filter 都能处理，那么为了防止不正确的关闭，那么`ContextLifecycleFilter` 相当于兜底操作，就是防止

   `ThreadLocal `没有被remove 掉。

   `ThreadLocal  `对应了一个 Thread，那么是不是意味着者Thread 处理完了，那么`ThreadLocal` 也随之 GC？

   所有 Servlet 均采用线程池，因此，不清空的话，可能会出现意想不到的情况。除非，每次都异常！（这种情况也要依赖于线程池的实现）。