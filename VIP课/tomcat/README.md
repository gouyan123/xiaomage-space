# 咕泡学员 [Tomcat](http://tomcat.apache.org/) 系列课钢

## 简介

The Apache Tomcat® software is an open source implementation of the Java Servlet, JavaServer Pages, Java Expression Language and Java WebSocket technologies. The Java Servlet, JavaServer Pages, Java Expression Language and Java WebSocket specifications are developed under the Java Community Process.

The Apache Tomcat software is developed in an open and participatory environment and released under the Apache License version 2. The Apache Tomcat project is intended to be a collaboration of the best-of-breed developers from around the world. We invite you to participate in this open development project. To learn more about getting involved, click here.

Apache Tomcat software powers numerous large-scale, mission-critical web applications across a diverse range of industries and organizations. Some of these users and their stories are listed on the PoweredBy wiki page.




## 讲师信息

小马哥，十余年Java EE 从业经验，架构师、微服务布道师。目前主要负责一线互联网公司微服务技术实施、架构衍进、基础设施构建等。重点关注云计算、微服务以及软件架构等领域。通过SUN Java（SCJP、SCWCD、SCBCD）以及Oracle OCA 等的认证。




## 课程详情

### [第一节 Tomcat 架构](lesson-1)

* 主要内容
  * 目录结构：介绍 Tomcat 目录结构，如`config` 、`webapps`等目录的用途，和各种配置文件的使用场景，如`server.xml`、`context.xml`以及`catalina.policy`等

  * 应用上下文：理解 Web 应用上下文 与 Servlet 之间的关系，掌握上下文路径以及其他相关属性的使用场景

  * 连接器：了解阻塞式 I/O 以及非阻塞式 I/O的配置方式，同时掌握请求连接的设置方法

  * 线程池：了解 Tomcat 线程池的配置方式以及理解它与 Java 标准实现的关系

  * Java EE 组件：JMX、JDNI 架构以及使用方法

    ​


### [第二节 嵌入式 Tomcat ](lesson-2)

* 主要内容
  * Maven 插件：利用Maven Tomcat 插件构建嵌入式 Tomcat 容器
  * API 编程：通过 Tomcat API 的角度，深入理解 Tomcat 运行机制以及组件之间的关系
  * Spring Boot ：深入探究 Spring Boot 利用嵌入式 Tomcat API 如何实现自定义容器





### [第三节 性能调优 ](lesson-3)

* 主要内容

    * Tomcat 配置调优：通过 Tomcat 内部配置的方式，比如：线程池管理、I/O 连接器配置、动静分离处理等
    * 程序调优：通过理解 Servlet 和 JSP 生命周期，合理地编码以及配置实现应用性能调优的目的
    * JVM 参数调优：掌握如何 JVM 参数的方式调优 Tomcat 服务器性能，如堆内外内存管理等

