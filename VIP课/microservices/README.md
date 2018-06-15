# 咕泡学院 微服务系列课钢

### [第一节 SpringApplication](lesson-1)

* 主要内容
  * 自定义 SpringApplication：介绍 `SpringApplication ` `SpringApplicationBuilder `API 调整
  * 配置 Spring Boot 源：理解 Spring Boot 配置源
  * SpringAppliation 类型推断：Web 应用类型、Main Class 推断
  * Spring Boot 事件：介绍 Spring Boot 事件与 Spring Framework 事件之间的差异和联系

### [第二节 Spring Web MVC 视图技术](lesson-2)

* 主要内容
  * Thymeleaf 视图技术：介绍新一代视图技术 Thymeleaf ，包括其使用场景、实际应用以及技术优势
  * 视图解析：介绍 Spring Web MVC 视图解析的过程和原理、以及内容协调视图处理器的使用场景
  * 国际化：利用`Locale`技术，实现视图内容的国际化

### [第三节 REST](lesson-3)

* 主要内容
  * REST 理论基础：基本概念、架构属性、架构约束、使用场景、实现框架（服务端、客户端）
  * REST 服务端实践：Spring Boot REST 应用、HATEOAS 应用、文档生成等
  * REST 客户端实践：传统浏览器、Apache HttpClient 、Spring RestTemplate 等相关实践

### [第四节 Spring WebFlux 原理](lesson-4)

* 主要内容

    * Reactive 原理：理解 Reactive 本质原理，解开其中的奥秘

    * WebFlux 使用场景：介绍 WebFlux 与 Spring Web MVC 的差异，WebFlux 真是的使用场景

    * WebFlux 整体架构：介绍 WebFlux、Netty 与 Reactor 之间的关系，对于 Spring Web MVC 架构深入理解

      

### [第五节 Spring WebFlux 运用](lesson-5)

* 主要内容

  * WebFlux 核心组件：包括 `HandlerMapping `、`HandlerAdapter ` 以及 `HandlerResultHandler `
  * WebFlux 编程模型运用：介绍 Annotation 驱动以及函数声明是编程模型的差异以及最佳实践



### [第六节 云原生应用（Cloud Native Applications）](lesson-6)

- 主要内容
  - Bootstrap 应用上下文：介绍 Spring Cloud 新引入的 Bootstrap 应用上下文，说明其与 Spring Framework 应用上下文之间的联系，进一步理解 Bootstrap 应用上下文在 Spring Boot 应用中的层次关系
  - 端点介绍：介绍 Spring Cloud 在 Spring Boot 基础上新引入的端点（Endpoint），比如：上下文重启:`/restart`、生命周期：`/pause`、`/resume`等

### [第七节 Spring Cloud 配置管理](lesson-7)

- 主要内容
  - Environment 端点：介绍`/env` 端点的使用场景，并且解读其源码，了解其中奥秘
  - 基本使用：介绍`@EnableConfigServer`、`Environment` 仓储
  - 分布式配置官方实现：介绍 Spring 官方标准分布式配置实现方式：JDBC 实现
  - 动态配置属性 Bean：介绍`@RefreshScope`基本用法和使用场景，并且说明其中的局限性
  - 健康指标：介绍 Spring Boot 标准端口（`/health`）以及 健康指标（Health Indicator）
  - 健康指标自定义实现：实现分布式配置的健康指标自定义实现

### [第八节 Spring Cloud 服务发现](lesson-8)

- 主要内容
  - Zookeeper 客户端：介绍 Spring Cloud Discovery 结合 Apache Zookeeper 客户端的基本使用方法，包括服务发现激活、Eureka 客户端注册配置 以及 API 使用等
  - Zookeeper 服务器：介绍 Apache Zookeeper 服务器作为服务注册中心的搭建方法

### [第九节 Spring Cloud 负载均衡](lesson-9)

- 主要内容
  - RestTemplate ：回顾 Spring Framework HTTP 组件 RestTemplate 的使用方法，结合 ClientHttpRequestInterceptor 实现简单负载均衡客户端
  - 整合 Netflix Ribbon：作为 Spring Cloud 客户端负载均衡实现 ，Netflix Ribbon 提供了丰富的组件，包括负载均衡器、负载均衡规则、PING 策略等，根据前章所积累的经验，实现客户端负载均衡

### [第十节 Spring Cloud 服务熔断](lesson-10)

- 主要内容
  - Spring Cloud Hystrix：作为服务端服务短路实现，介绍 Spring Cloud Hystrix 常用限流的功能，同时，说明健康指标以及数据指标在生产环境下的现实意义
  - 生产准备特性：介绍聚合数据指标 Turbine 、Turbine Stream，以及整合 Hystrix Dashboard

### [第十一节 Spring Cloud 服务调用](lesson-11)

- 主要内容
  - Spring Cloud Feign ：介绍声明式客户端REST实现 Spring Cloud Feign的使用方式（如`@EnableFeignClients` 、 `@FeignClient`)，结合 Eureka 构建分布式服务应用
  - 整合支持：Spring Cloud Feign 整合 Hystrix 以及 Ribbon

### [第十二节 Spring Cloud Gateway](lesson-12)

- 主要内容
  - 核心概念：介绍服务网关使用场景、服务能力、依赖关系、架构以及类型
  - Ribbon 整合
  - Hystrix 整合



### [第十三节 Spring Cloud Stream (上)](lesson-13)

- 主要内容
  - RabbitMQ
  - Spring RabbitMQ
  - Spring Boot RabbitMQ

### [第十四节 Spring Cloud Stream (下)](lesson-14)

- 课程内容（1.5 小时）
  - Spring Cloud Stream 
  - RabbitMQ 绑定实现



### [第十五节 Spring Cloud Sleuth](lesson-15)

- 课程内容（1.5 小时）
  - 分布式应用跟踪
  - ZipKin 整合

### [第十六节 Spring Cloud 整体回顾](lesson-16)

- 课程内容（1.5 小时）
  - Spring Boot + Spring Cloud 系列整体回顾

