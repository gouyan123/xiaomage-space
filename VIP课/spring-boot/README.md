# 咕泡学员 [Spring Boot](http://projects.spring.io/spring-boot/) 系列课钢

## 简介

Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run". We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. Most Spring Boot applications need very little Spring configuration.


## 讲师信息

小马哥，十余年Java EE 从业经验，架构师、微服务布道师。目前主要
负责一线互联网公司微服务技术实施、架构衍进、基础设施构建等。重点关注云计算、微服务以及软件架构等领域。通过SUN Java（SCJP、SCWCD、SCBCD）以及Oracle OCA 等的认证。


## 课程详情

### [第一节 Spring Boot 初体验](lesson-1)

* 主要内容
  * Spring Boot 技术栈：介绍 Spring Boot 完整的技术栈，比如 Web 应用、数据操作、消息、测试以及运维管理等
  * Spring Boot 构建方式：介绍图形化以及命令行方式构建 Spring Boot 项目
  * Spring Boot 多模块应用：构建分层、多模块 Spring Boot 应用
  * Spring Boot 运行方式：分别介绍 IDEA 启动、命令行启动以 Maven 插件启动方式
  * Spring Boot 简单应用：使用 Spring Web MVC 以及 Spring Web Flux 技术，编程简单应用
  * 理解 Spring Boot 三大特性：自动装配、嵌入式容器、为生产准备的特性


### [第二节 Spring Web MVC](lesson-2)

* 主要内容
  * Spring Web MVC 介绍：整体介绍 Spring Web MVC 框架设计思想、功能特性、以及插播式实现
  * Spring Web MVC 实战：详细说明`DispatcherServlet`、`@Controller`和`@RequestMapping`的基本原理、`@RequestParam`、`@RequestBody`和`@ResponseBody`使用方式、以及它们之间关系
  * 映射处理：介绍`DispatcherServlet`与`RequestMappingHandlerMapping`之间的交互原理，`HandlerInterceptor`的职责以及使用
  * 异常处理：介绍`DispatcherServlet`中执行过程中，如何优雅并且高效地处理异常的逻辑，如归类处理以及提供友好的交互界面等
  * Thymeleaf 视图技术：介绍新一代视图技术 Thymeleaf ，包括其使用场景、实际应用以及技术优势
  * 视图解析：介绍 Spring Web MVC 视图解析的过程和原理、以及内容协调视图处理器的使用场景
  * 国际化：利用`Locale`技术，实现视图内容的国际化


### [第三节 REST](lesson-3)

* 主要内容
  * REST 理论基础：基本概念、架构属性、架构约束、使用场景、实现框架（服务端、客户端）
  * REST 服务端实践：Spring Boot REST 应用、HATEOAS 应用、文档生成等
  * REST 客户端实践：传统浏览器、Apache HttpClient 、Spring RestTemplate 等相关实践


### [第四节 数据库 JDBC](lesson-4)

* 主要内容

    * 数据源（DataSource）：分别介绍嵌入式数据源、通用型数据源以及分布式数据源
    * 事务（Transaction）：介绍事务原理，本地事务和分布式事务的使用场景
    * JDBC（JSR-221）：介绍JDBC 核心接口，数据源、数据库连接、执行语句、事务等核心API的使用方法


### [第五节 验证](lesson-5)

* 主要内容

  * Bean Validation（JSR-303）：介绍 Java Bean 验证、核心 API、实现框架 Hibernate Validator
    * Apache commons-validator ：介绍最传统 Apache 通用验证器框架，如：长度、邮件等方式
    * Spring Validator：介绍 Spring 内置验证器 API、以及自定义实现

