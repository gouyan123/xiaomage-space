# Spring Boot 初体验



## 基本概念

应用分为两个方面：功能性、非功能性

功能性：系统所设计的业务范畴

非功能性：安全、性能、监控、数据指标（CPU利用率、网卡使用率）

Spring Boot 规约大于配置，大多数组件，不需要自行配置，而是自动组装！简化开发，大多数情况，使用默认即可！

production-ready 就是非功能性范畴！



独立Spring 应用，不需要外部依赖，依赖容器（Tomcat）

嵌入式 Tomcat Jetty



外部配置：启动参数、配置文件、环境变量

外部应用：Servlet 应用、Spring Web MVC、Spring Web Flux、WebSocket、WebService

SQL：JDBC、JPA、ORM

NoSQL（Not Only SQL）：Redis、ElasticSearch、Hbase

Mono : 0 - 1 元素，Optional

Flux：0 - N 个元素，类似于 Iterable 或者 Collection



Req -> WebFlux -> 1 - N 线程执行任务执行函数式任务

它是推的方式！

Java 9 里面API 称之为 Flow（流）

Publisher -> publish(1)

Subscription（1）：订阅消息

Subs(A)#onNext() -> Subs(B)#onNext() -> Subs(C)#onNext()



Reactive 是推模式（Push）

Iterator 是拉模式（Pull）





## 构建多模块应用

1. 修改主工程类型<packaging>jar</packaging> -> pom
2.  新建 web 工程，讲遗留代码移动到 web java 目录下
3. 再从 web 工程，独立出 model 工程
4. 将 web 工程依赖 model 工程
5. 重复步骤 3，独立出 persistence
6. 再从 persistence 添加 model 的依赖
7. 最终依赖关系 web -> persistence -> model



## 构建可执行 jar 或者 war

web-1.0.0-SNAPSHOT.jar中没有主清单属性？

需要一个 Spring Boot 的插件：

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
  </plugins>
</build>
```



>  jar 规范里面，有一个 MANIFEST.MF，里面有一个 Main-Class 的属性。API：`java.util.jar.Manifest#getAttributes`



#### 从 jar 切换成 war 打包方式

1. 修改 <packaging> 成 war
2. 创建` webapp/WEB-INF` 目录（相对于`src/main`
3. 新建一个空的`web.xml`

> 注意 步骤2 和 步骤3 是为了绕过war插件的限制，小马哥笨办法

或者使用

```xml
<plugin>
                <artifactId>maven-war-plugin</artifactId>
                <configuration>
                   <failOnMissingWebXml>false</failOnMissingWebXml>
                </configuration>
            </plugin>
```





#### 注意事项

* BOOT-INF 是 Spring Boot 1.4 开始才有

* 当使用依赖或者插件时，如果版本是 Milestone的时候，需要增加：

  ```xml
  <repositories>
      <repository>
          <id>spring-milestones</id>
          <name>Spring Milestones</name>
          <url>https://repo.spring.io/libs-milestone</url>
          <snapshots>
              <enabled>false</enabled>
          </snapshots>
      </repository>
  </repositories>

  <pluginRepositories>
      <pluginRepository>
          <id>spring-snapshots</id>
          <url>http://repo.spring.io/snapshot</url>
      </pluginRepository>
      <pluginRepository>
          <id>spring-milestones</id>
          <url>http://repo.spring.io/milestone</url>
      </pluginRepository>
  </pluginRepositories>
  ```

* META-INF/MANIFEST.MF 里面有指定两个属性

  * Main-Class
  * Start-Class
    * 例子：
    * Main-Class: org.springframework.boot.loader.JarLauncher
      Start-Class: com.gupao.App

* 除了 jar 或者 war 启动的方式，还有目录启动方式

  * 目录启动方式可以帮助解决老旧的jar 不支持 Spring Boot 新方式，比如老版本的 MyBatis
    * 如果是 jar 包，解压后，跳转解压目录，并且执行`java`命令启动，启动类是 org.springframework.boot.loader.JarLauncher
    * 如果是 war包，解压后，跳转解压目录，并且执行`java`命令启动类是org.springframework.boot.loader.WarLauncher

#### 查看端口

注意控制台：Netty started on port(s): 8080

--server.port 随机可用端口



自动装配的模式



XXX-AutoConfiguration





### 问答互动

#### 问：webFluxConfiguration里面的映射路径和controller 里面的路径有什么区别吗

答：基本上是没有区别的，注意，不重复定义，或者URL 语义有重复！



#### 问：webFlux不是跟mvc不能一起吗，怎么一起启动了

答：spring-boot-starter-webmvc 和 spring-boot-starter-webflux 可以放在同一个应用，可是 webflux 不会工作，默认使用webmvc，webflux 不会被采用。其实 webflux 是兼容 Annotation 驱动，比如`@RequestMapping`



#### 问：webFlux可以定义restFull吗

答：可以的，支持的！



#### 问：spring的老项目迁移到springboot，怎么弄

答：老的XML 方式采用 `@ImportResource` 导入！



#### 问：嵌入式tomcat如何调优

答：第一种通过 application.properties文件调整配置参数

第二种通过接口回调：

`TomcatConnectorCustomizer`

`TomcatContextCustomizer`

