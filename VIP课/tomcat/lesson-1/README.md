# Tomcat 架构



## 目录结构



### conf 目录



`catalina.policy` :  Tomcat 安全策略文件，控制 JVM 相关权限，具体可以参考`java.security.Permission`

`catalina.properties` : Tomcat Catalina 行为控制配置文件，比如 Common ClassLoader

`logging.properties`  : Tomcat 日志配置文件，JDK Logging

`server.xml`  :  Tomcat Server 配置文件

 * `GlobalNamingResources` : 全局 JNDI 资源

`context.xml` : 全局 Context 配置文件

`tomcat-users.xml` : Tomcat 角色配置文件，（Realm 文件实现方式）、

`web.xml` : Servlet 标准的 web.xml 部署文件，Tomcat 默认实现部分配置入内：

 * `org.apache.catalina.servlets.DefaultServlet`
 * `org.apache.jasper.servlet.JspServlet`



### lib 目录

Tomcat 存放公用类库

`ecj-*.jar`  : Eclipse Java 编译器

`jasper.jar` : JSP 编译器



### logs 目录

`localhost.${date}.log` :  当 Tomcat 应用起不来的时候，多看该文件，比如：类冲突

 * `NoClassDefFoundError`
 * `ClassNotFoundException`

`catalina.${date}.log` : 控制台输出，`System.out` 外置





## webapps 目录

简化 web 应用部署的方式









## 部署 Web 应用



### 方法一：放置在 `webapps `目录



直接拖过去



### 方法二： 修改 `confi/server.xml`



添加`Context` 元素：

```xml
<Context docBase="${webAppAbsolutePath}" path="/" reloadable="true" />
<Context docBase="${webAppAbsolutePath}" path="/tomcat" reloadable="true" />
```

熟悉配置元素可以参考`org.apache.catalina.core.StandardContext` setter 方法



`Container`

 * `Context`



该方式不支持动态部署，建议考虑在生产环境使用。



### 方法三：独立 `context` xml 配置文件



首先注意 `conf\Catalina\localhost`



独立 context XML 配置文件路径：`${TOMCAT_HOME}/conf/Catalina/localhost` + `${ContextPath}` .xml



注意：该方式可以实现热部署，因此建议在开发环境使用。



### I/O 连接器



参考文件：https://tomcat.apache.org/tomcat-7.0-doc/config/http.html



实现类：`org.apache.catalina.connector.Connector`



注意实现：

```java
    public void setProtocol(String protocol) {

        if (AprLifecycleListener.isAprAvailable()) {
            if ("HTTP/1.1".equals(protocol)) {
                setProtocolHandlerClassName
                    ("org.apache.coyote.http11.Http11AprProtocol");
            } else if ("AJP/1.3".equals(protocol)) {
                setProtocolHandlerClassName
                    ("org.apache.coyote.ajp.AjpAprProtocol");
            } else if (protocol != null) {
                setProtocolHandlerClassName(protocol);
            } else {
                setProtocolHandlerClassName
                    ("org.apache.coyote.http11.Http11AprProtocol");
            }
        } else {
            if ("HTTP/1.1".equals(protocol)) {
                setProtocolHandlerClassName
                    ("org.apache.coyote.http11.Http11Protocol");
            } else if ("AJP/1.3".equals(protocol)) {
                setProtocolHandlerClassName
                    ("org.apache.coyote.ajp.AjpProtocol");
            } else if (protocol != null) {
                setProtocolHandlerClassName(protocol);
            }
        }

    }
```







## 问答互动

### 问题一：如果配置path的话 是以文件名为主 还是 以配置的为主



独立 context XML 配置文件时，设置 `path` 属性是无效的。





### 问题二：根独立 context XML 配置文件路径



`${TOMCAT_HOME}/conf/${Engine.name}/${HOST.name}/ROOT.xml`





### 问题三：如果实现热部署



调整 `<context>` 元素中的属性`reloadable="true" `





### 问题四：连接器里面的线程池 是用的哪个线程池



注意`conf/server.xml` 文件中的一段注释：

```xml
<Connector executor="tomcatThreadPool"
               port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
```



`org.apache.catalina.Executor`:

```java
public interface Executor extends java.util.concurrent.Executor, Lifecycle {
    public String getName();

    /**
     * Executes the given command at some time in the future.  The command
     * may execute in a new thread, in a pooled thread, or in the calling
     * thread, at the discretion of the <tt>Executor</tt> implementation.
     * If no threads are available, it will be added to the work queue.
     * If the work queue is full, the system will wait for the specified
     * time until it throws a RejectedExecutionException
     *
     * @param command the runnable task
     * @throws java.util.concurrent.RejectedExecutionException if this task
     * cannot be accepted for execution - the queue is full
     * @throws NullPointerException if command or unit is null
     */
    void execute(Runnable command, long timeout, TimeUnit unit);
}
```



标准实现：`org.apache.catalina.core.StandardThreadExecutor` 将连接处理交付给 Java 标准线程池：



`org.apache.tomcat.util.threads.ThreadPoolExecutor`。





### 问题五：JNDI 能不能稍微说下 之前只是在数据源的时候用过，但是不是太理解



```xml
<Context ...>
  ...
  <Resource name="mail/Session" auth="Container"
            type="javax.mail.Session"
            mail.smtp.host="localhost"/>
  ...
</Context>
```



```java
Context initCtx = new InitialContext();
Context envCtx = (Context) initCtx.lookup("java:comp/env");
Session session = (Session) envCtx.lookup("mail/Session");

Message message = new MimeMessage(session);
message.setFrom(new InternetAddress(request.getParameter("from")));
InternetAddress to[] = new InternetAddress[1];
to[0] = new InternetAddress(request.getParameter("to"));
message.setRecipients(Message.RecipientType.TO, to);
message.setSubject(request.getParameter("subject"));
message.setContent(request.getParameter("content"), "text/plain");
Transport.send(message);
```









