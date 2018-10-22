# 第六节 云原生应用（Cloud Native Applications）



## Spring Cloud 版本

`Finchley.RELEASE`

学好 Spring Cloud 需要 Spring Boot 基础

学好 Spring Boot 需要深刻理解 Spring Framework



## 主要内容



### Bootstrap 应用上下文



#### 云原生

> [Cloud Native](https://pivotal.io/platform-as-a-service/migrating-to-cloud-native-application-architectures-ebook) is a style of application development that encourages easy adoption of best practices in the areas of continuous delivery and value-driven development. 
>
>  [12-factor Applications](http://12factor.net/) 
>
> 分布式配置
>
> CI/CD
>
> 远程调用
>
> 消息服务
>
> 并发



[官方文档](http://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html#_cloud_native_applications)



Spring 应用上下文的层次？

> `Component` “派生性”



#### Spring 应用上下文 - `ApplicationContext`





#### 理解上下文层次

`BeanFactory` 与 `ApplicationContext`

类层次性，`ApplicationContext` 扩展 `ListableBeanFactory` 以及 `HierarchicalBeanFactory`

-> `AppliationContext`  继承 `BeanFactoy`

从结构而言，`ApplicationContext` 关联了 `BeanFactory` 实现

* `AbstractRefreshableApplicationContext`
  * 属性 beanFactory `DefaultListableBeanFactory`

设计模式，装饰者模式，继承并扩展，底层实现基于被扩展示例。



`BeanFactory` 才是真正 Bean 容器，管理 Bean 生命周期。

`ApplicationContext` 包含了 `BeanFactory` 职责，并且还有其他。



`ApplicationContext` 继承了  `HierarchicalBeanFactory`，给开发人员的提示？



`ApplicationContext` Bean 生命周期管理能力，来自于 `BeanFactory`，并且它又是  `HierarchicalBeanFactory` 子接口，说明它具备 `BeanFactory` 的层次性关系。

同时，它也有 `getParent()` 方法返回双亲 `ApplicationContext`，

其子接口 `ConfigurableApplicationContext` 拥有设置双亲 `ApplicationContext` 的能力。

类比：

>- Parent `BeanFactory` (管理 10 个Bean)
>  - Child `BeanFactory`  (管理 20 个Bean)
>
>
>
>- Parent `ClassLoader`  (加载 10 个类)
>  - Child `ClassLoader` （加载 20 个类）
>
>



Spring Boot 1.x 默认情况一个 ApplicationContext

如果独立管理上下文 ，有两个 ApplicationContext



Spring Boot 2.0 合并一个 ApplicationContext

Spring Cloud 会增加 Bootstrap ApplicationContext



```java
@EnableAutoConfiguration
public class SpringBootApplicationBootstrap {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.setId("小马哥");
        context.refresh();

        new SpringApplicationBuilder(SpringBootApplicationBootstrap.class)
                .parent(context) // 显式地设置双亲上下文
                .run(args);

    }
}
```





子上下文必须双亲上下文启动，bootstrap -> context-1 



application-1 parentId -> 小马哥

小马哥 parentId -> bootstrap

bootstrap parentId -> `null`



* bootstrap (Ready)
  * 小马哥 (Ready)
    * application-1 (Ready)

是不是意味着 bootstrap 要提早加载什么资源？



#### 理解 Bootstrap 应用上下文



##### 关键调用实现

`org.springframework.boot.builder.ParentContextApplicationContextInitializer`

* Spring Boot 实现

`org.springframework.cloud.bootstrap.BootstrapApplicationListener`

* Spring Cloud 实现
* 监听事件
  * `ApplicationEnvironmentPreparedEvent`
    * 推理意思：`Environment` 已经准备好（被调整）



技术关联： Spring 应用上下文层次，Spring 事件



##### 理解 Environment

[ Spring Boot 外部化配置官方文档](https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/reference/htmlsingle/#boot-features-external-config)



#### 理解 Spring Boot Actuator Endpoints



##### 开放所有Web 管理 Endpoints

```properties
# 开放 所有Web 管理 Endpoints
management.endpoints.web.exposure.include = *
```



启动日志输出

```json
  // http://localhost:9091/actuator

{
  "_links": {
    "self": {
      "href": "http://localhost:9091/actuator",
      "templated": false
    },
    "auditevents": {
      "href": "http://localhost:9091/actuator/auditevents",
      "templated": false
    },
    "beans": {
      "href": "http://localhost:9091/actuator/beans",
      "templated": false
    },
    "health": {
      "href": "http://localhost:9091/actuator/health",
      "templated": false
    },
    "conditions": {
      "href": "http://localhost:9091/actuator/conditions",
      "templated": false
    },
    "configprops": {
      "href": "http://localhost:9091/actuator/configprops",
      "templated": false
    },
    "env": {
      "href": "http://localhost:9091/actuator/env",
      "templated": false
    },
    "env-toMatch": {
      "href": "http://localhost:9091/actuator/env/{toMatch}",
      "templated": true
    },
    "info": {
      "href": "http://localhost:9091/actuator/info",
      "templated": false
    },
    "loggers": {
      "href": "http://localhost:9091/actuator/loggers",
      "templated": false
    },
    "loggers-name": {
      "href": "http://localhost:9091/actuator/loggers/{name}",
      "templated": true
    },
    "heapdump": {
      "href": "http://localhost:9091/actuator/heapdump",
      "templated": false
    },
    "threaddump": {
      "href": "http://localhost:9091/actuator/threaddump",
      "templated": false
    },
    "metrics-requiredMetricName": {
      "href": "http://localhost:9091/actuator/metrics/{requiredMetricName}",
      "templated": true
    },
    "metrics": {
      "href": "http://localhost:9091/actuator/metrics",
      "templated": false
    },
    "scheduledtasks": {
      "href": "http://localhost:9091/actuator/scheduledtasks",
      "templated": false
    },
    "httptrace": {
      "href": "http://localhost:9091/actuator/httptrace",
      "templated": false
    },
    "mappings": {
      "href": "http://localhost:9091/actuator/mappings",
      "templated": false
    },
    "refresh": {
      "href": "http://localhost:9091/actuator/refresh",
      "templated": false
    },
    "features": {
      "href": "http://localhost:9091/actuator/features",
      "templated": false
    }
  }
}
```



##### 服务上下文和管理上线独立



```properties
# 设置 Web 管理端口
management.server.port = 9091
```



```json
// http://localhost:9091/actuator

{
  "_links": {
    "self": {
      "href": "http://localhost:9091/actuator",
      "templated": false
    },
    "health": {
      "href": "http://localhost:9091/actuator/health",
      "templated": false
    },
    "info": {
      "href": "http://localhost:9091/actuator/info",
      "templated": false
    }
  }
}
```





##### 获取环境端口(/actuator/env)



访问端口：`/actuator/env`



##### /actuator/pause 和 /actuator/resume 端口



 /actuator/pause -> `ApplicationContext#stop()` -> Spring LifeCycle Beans `stop()`

LifeCycle 实例（ApplicationContext）不等于 LifeCycle Bean



 /actuator/resume -> `ApplicationContext#start()` -> Spring LifeCycle Beans `start()`



## 提问

https://ask.gupaoedu.com/









#### `Environment`



#### Spring  事件

事件类：`ApplicationEvent`

事件监听器： `ApplicationListener`

事件广播器：`ApplicationEventMulticaster` / `SimpleApplicationEventMulticaster` （唯一）

事件发送器：`ApplicationEventPublisher`









### 端点介绍





## 下节课预习



### 相关视频

#### 去年 VIP Spring Cloud 系列

- [第一节 Spring Cloud Config Client](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-1)

- [第二节 Spring Cloud Netflix Eureka](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-2)

  

