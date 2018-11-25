# 第十六节 整体回顾



## 经验之谈

1. 任何结论，需要程序检验（稳定重现）
2. 经常写代码、多看、多思考
3. 训练思维模式（抽象思维、辩证思维、逆向思维、发散思维）
4. 原始积累（JDK、框架：Spring、Tomcat、Log4j 等等）
5. 少对同伴代码指点，方案上可以讨论（Code Review 思维是否严谨）
6. 乐于分享（演讲、技术沙龙、讨论会议等）
7. 如何选择性看书（仅供参考）



## Java 基础

### 工具

#### Java 编译器

`javax.tools.JavaCompiler`



#### Jar  = Zip

Spring Boot Fat Jar 压缩率 = 0



#### Java Debug

JDB



### ClassLoader

* Class
  * 

* ClassLoader
  * Class
  * 资源管理
  * 并行加载 Class 1.7
  * 并发（Thread）
  * META Space



###  Beans 

* 内省（`Introspector`)： Java Bean 规范 + 反射（Reflection） + 引用（Reference）
  * BeanInfo
  * PropertyDescriptor
  * ...

> Spring 例子：`PropertyEditorRegistrar` 注册 `PropertyEditor`
>
> `PropertyEditor` 字符文本装换位需要的类型
>
> ```xml
> <bean class="User">
>     <property name="id" value="9" /> <!-- String to Long -->
>     <property name="age" value="32" /> <!-- String to Integer -->
> </bean>
> ```



## Java 编程模型演变

### 面向对象编程（OOP）

* 封装性
* 派生性
* 多态性

#### 设计模式

* GoF 23
* Reactor/Proactor

### Java 1.2 反射编程

### Java 1.3 AOP

### Java 1.4 NIO（Non-Blocking）

### Java 1.5 泛型/并发编程

### Java 1.6 脚本编程（JavaScript）

### Java 1.7 NIO 2

### Java 1.8 Lambda 编程

### Java 9 模块化/Reactive 编程





## Spring Framework 简史

### 核心特性 

#### IoC/DI

基于 Java 反射、Java Beans 内省

#### AOP

* 接口类型基于 Java AOP Proxy
* 类类型基于 CgLib 提升实现

#### 资源管理

* ClassLoader
* URL
* I/O

#### 事件

* Java 标准事件/监听机制
  * `java.util.EventObject`
  * `java.util.EventListener`
* Java 观察者模式
  * `java.util.Observer`
  * `java.util.Observable`

#### 国际化

* `java.util.ResourceBundle`
  * `java.util.PropertyResourceBundle`
* `java.text.MessageFormat`







#### 验证

* Validator
* Bean Validation（JSR-303）
  * `LocalValidatorFactoryBean`
    * `javax.validation.ValidatorFactory`
    * `org.springframework.validation.SmartValidator`

#### 数据绑定

* `DataBinder`

#### 类型转换

* `java.beans.PropertyEditor`
* `Convertor`
* `ConversionService`

#### Spring EL

* #### JSP EL

* OGNL



### 数据访问

#### 事务

* JDBC Transaction
* JTA（Java Transaction API）
* JTS（Java Transaction Service）

#### JDBC

* JDBC -> JdbcTemplate

#### ORM

* Hibernate
* iBatis

#### XML

* SAX（Simple API for XML）
* XML Stream
* JAXB（Java API for XML Binding）
* DOM（Document Object Model）



### Web Servlet

#### Spring MVC

* 早期借鉴 Struts
* 中后期借鉴 JAX-RS（REST Java 规范）



### Web Reactive

#### Spring WebFlux

* Reactor
  * RxJava
* Netty



Spring Framework 一种编程模型

XML 配置多 -> 注解配置多



## Spring Boot 特性

* 自动装配：实现规约大于配置（减少配置）
* Production-Ready：完善或简化 Spring 应用的运维体系
  * 外部化配置：通过外部配置调整应用内部行为
* 嵌入式容器：装配嵌入式 Web 容器
  * Servlet 容器
  * Netty Web 容器



## Spring Cloud 特性

* 服务发现
* 远程调用
  * 客户端申明（Feign）
  * 服务熔断（Hystrix）
  * 负载均衡（Ribbon）
  * 服务调用链路跟踪（Sleuth）
* 分布式配置
  * 版本系统（Git）
  * 分布式实现（自定义实现）
* 异步消息
  * 消息总线（Spring Cloud Bus）
  * 消息整合（Spring Cloud Stream）
* 网关
  * Spring Cloud 1.x / 2.x Zuul
  * Spring Cloud Gateway



## 更多

### 分布式事务

#### 最终一致性

* 基于消息的最终一致性

### 安全