代码位置：VIP课/microservices/source/microservices-project/spring-cloud-project/spring-cloud-client-application
---
Java事件：
事件：所有事件类型扩展 java.util.Event；
事件监听器套路：
1 参数类型：java.util.Event对象及其子类；事件源 JPA EntityListener；
2 监听方法没有返回值：例外 Spring @EventListener；
3 监听方法不会 throws Exception；
创建 com.gupao.micro.services.spring.cloud.client.event.GUIEvent类，通过GUI桌面程序，了解监听者模式：

---
Spring 事件：
Spring 事件基类 ApplicationEvent，相对于 java.util.EventObject增加事件发生时间戳 timestamp；
多事件监听器接口：
Spring 都是单监听器，因为 Java8之前 Interface没有 默认实现方法default method，需要 Adapter抽象类提供空实现；
Adapter有 2种模式：1 接口空实现；2 A 和 B没有层次关系，需要A变成B，即A适配B；

内建事件
ContextRefreshedEvent
ConfigurableApplicationContext#refresh()
ContextClosedEvent
ConfigurableApplicationContext#close()
ContextStartedEvent
ConfigurableApplicationContext#start()
ContextStoppedEvent
ConfigurableApplicationContext#stop()
RequestHandledEvent
FrameworkServlet#publishRequestHandledEvent
注意：事件/监听者器，注意事件（语法）时态。
创建 com.gupao.micro.services.spring.cloud.client.event.SpringEvent类，观察 Spring Event；
注意 Lifecycle接口，代码如下：
public interface Lifecycle {
    void start();
    void stop();
    boolean isRunning();
}
创建 com.gupao.micro.services.spring.cloud.client.event.SpringAnnotationDrivenEvent类，通过注解实现
事件监听；
创建 com.gupao.micro.services.spring.cloud.client.controller.SpringEventController发个事件给自己，自己
去监听；要启动 zookeepr(注册中心)和rabbitmq；多个应用之间通过发送事件，实现交互，体现了 面向事件编程的思想；


Spring 事件监听器

所有 Spring 事件监听器实现 ApplicationListener

SpringBoot事件：

SpringCloud事件：

Event Bus 事件总线：

