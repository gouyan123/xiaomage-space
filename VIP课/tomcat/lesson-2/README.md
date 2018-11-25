# 嵌入式 Tomcat



## Tomcat 架构

![](Tomcat_Architecture-680x428.png)





## Web 技术栈



### Servlet 技术栈



### Web Flux（Netty）



BIO NIO



## Web 自动装配

### API 角度分析

Servlet 3.0 + API 实现  `ServletContainerInitializer`



### 容器角度分析

传统的 Web 应用，将 webapp 部署到 Servlet 容器中。

嵌入式 Web 应用，灵活部署，任意指定位置（或者通过复杂的条件判断）

Tomcat 7 是 Servlet 3.0 的实现，`ServletContainerInitializer`

Tomcat 8 是 Servlet 3.1 的实现，NIO `HttpServletRequest`、`HttpServletResponse`



> NIO 并非一定能够提高性能，比如请求数据量较大，NIO 性能比 BIO 还要差
>
> NIO 多工，读、写，同步的



### jar 启动

`java -jar` 或者 `jar ` 读取 .jar  `META-INF/MANIFEST.MF` ，其中属性 `Main-Class` 就是引导类所在。

> 参考 JDK API ： `java.util.jar.Manifest`





## Tomcat Maven 插件



### Tomcat 7 Maven 插件

```xml
<groupId>org.apache.tomcat.maven</groupId>
<artifactId>tomcat7-maven-plugin</artifactId>
<version>2.1</version>
```



```properties
Manifest-Version: 1.0
Main-Class: org.apache.tomcat.maven.runner.Tomcat7RunnerCli
```



得出 Tomcat 7 可执行 jar 引导类是`org.apache.tomcat.maven.runner.Tomcat7RunnerCli`





## Tomcat 7 API 编程



### 确定 Classpath 目录



classes 绝对路径：`E:\Downloads\tomcat\target\classes`



```java
String classesPath = System.getProperty("user.dir")
        + File.separator + "target" + File.separator + "classes";
```



### 创建 `Tomcat` 实例



`org.apache.catalina.startup.Tomcat`



Maven 坐标：`org.apache.tomcat.embed:tomcat-embed-core:7.0.37`



### 设置 `Host `对象

```java
// 设置 Host
Host host = tomcat.getHost();
host.setName("localhost");
host.setAppBase("webapps");
```



### 设置 Classpath



Classpath 读取资源：配置、类文件



`conf/web.xml` 作为配置文件，并且放置 Classpath 目录下（绝对路径）



设置 `DemoServlet`

```java
 // 添加 DemoServlet 到 Tomcat 容器
            Wrapper wrapper = tomcat.addServlet(contextPath, "DemoServlet", new DemoServlet());
            wrapper.addMapping("/demo");
```





## Spring Boot 嵌入式 Tomcat 编程



### 实现 `EmbeddedServletContainerCustomer`



```java
@Configuration
public class TomcatConfiguration implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        System.err.println(container.getClass());

        if (container instanceof TomcatEmbeddedServletContainerFactory) {

            TomcatEmbeddedServletContainerFactory factory=
                    (TomcatEmbeddedServletContainerFactory) container;

//            Connector connector = new Connector();
//            connector.setPort(9090);
//            connector.setURIEncoding("UTF-8");
//            connector.setProtocol("HTTP/1.1");
//            factory.addAdditionalTomcatConnectors(connector);


        }

    }
}
```





### 自定义 `Context`



实现`TomcatContextCustomizer`



```java
  // 相当于 new TomcatContextCustomizer(){}
  factory.addContextCustomizers((context) -> { // Lambda
    if (context instanceof StandardContext) {
      StandardContext standardContext = (StandardContext) context;
      // standardContext.setDefaultWebXml(); // 设置
    }
  });
```



### 自定义 `Connector`



实现 `TomcatConnectorCustomizer`

```java
  // 相当于 new TomcatConnectorCustomizer() {}
  factory.addConnectorCustomizers(connector -> {
    connector.setPort(12345);
  });
```



## 问答

1. 内嵌tomcat是不是比单独的tomcat不是在某方面具有一些优势？

   嵌入式 Tomcat 或者嵌入式 Web 容器可以不依赖文件目录，比如在 Docker 场景使用方便。

2. 内置的tomcat 和 外部的tomcat 性能有多大差别?生产线上建议用哪个?

   嵌入式 Tomcat 和 传统 Tomcat 性能可以说一样，现在非常多的生产环境 Spring Boot 嵌入式 - 嵌入式 Tomcat

3. Spring中pre实例化和pre初始化区别(刚刚有提到)

   在 Spring 早起版本中，先有初始化生命周期 - `org.springframework.beans.factory.config.BeanPostProcessor`

   后来 Spring 1.2 ，提供新的扩展接口（`BeanPostProcessor`）：

   `org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor`

   ​

4. Tomcat API 主要是讲tomcat运行原理吗？生产环境用哪种比较多？

   传统 Tomcat 和 嵌入式 Tomcat 都有在生产环境使用。

   ​

5. Tomcat 热部署原理能给讲下吗？

   `org.apache.catalina.Lifecycle`

​    监控资源变化，Class 变化、配置变化了。 Tomcat 决定重启 Context

​	`org.apache.catalina.Context` 

​	Tomcat 自动加载

```java
 public void setReloadable(boolean reloadable)
```

