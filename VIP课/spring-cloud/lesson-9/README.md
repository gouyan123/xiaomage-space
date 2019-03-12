-----
ppt内容：
主要议题：
1、Spring Cloud Stream
2、Spring Cloud Stream Kafka Binder
3、Spring Cloud Stream RabbitMQ Binder
-----
学到内容：
1、只要 2个端点 即客户端和服务端 想要通信，就可以通过 Spring Cloud Stream实现；
-----
# Spring Cloud Stream（下）
RabbitMQ与Kafka比较：
1、RabbitMQ 实现AMQP(Advanced Message Queue Protocol)、JMS(Java Message Protocol)规范；
2、Kafka 实现 相对松散的消息队列协议，具有瞬时性，高可用性特点；
《企业整合模式》： [Enterprise Integration Patterns](http://www.eaipatterns.com/)

## [Spring Cloud Stream](https://cloud.spring.io/spring-cloud-stream/)
### SpringCloudStream的一些基本概念：
#### Source：存入器，往Source里面存，近义词：Producer、Publisher；
#### Sink：接收器，从Sink往外取，近义词：Consumer、Subscriber；
#### Processor：转换器/处理器，对于上流而言是 Sink，对于下流而言是 Source；
重点：只要 2个端点 即客户端和服务端 想要通信，就可以通过 Spring Cloud Stream实现；
> Reactive Streams 基本组成如下: 
>
>  * Publisher
>  * Subscriber
>  * Processor

## Spring Cloud Stream Binder : [Kafka](http://kafka.apache.org/)
### 继续沿用 [spring-cloud-stream-kakfa-demo](spring-cloud-stream-kakfa-demo) 工程
#### 启动 Zookeeper
#### 启动 Kafka
> 消息大致分为两个部分：
>
> * 消息头（Headers）
>
>
> * 消息体（Body/Payload）
#### 定义标准消息发送源
```java
@Component
@EnableBinding({Source.class})
public class MessageProducerBean {

    @Autowired
    @Qualifier(Source.OUTPUT) // Bean 名称
    private MessageChannel messageChannel;

    @Autowired
    private Source source;
}
```
#### 自定义标准消息发送源
```java
package com.gupao.springcloudstreamkafka.stream.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

/**
 * 自定义消息源
 */
public interface MessageSource {

    /**
     * 消息来源的管道名称："gupao"
     */
    String OUTPUT = "gupao";

    @Output(OUTPUT)
    MessageChannel gupao();
}
```
```java
package com.gupao.springcloudstreamkafka.stream.producer;

import com.gupao.springcloudstreamkafka.stream.messaging.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 消息生成者 Bean
 */
@Component
@EnableBinding({Source.class,MessageSource.class})
public class MessageProducerBean {

    @Autowired
    @Qualifier(Source.OUTPUT) // Bean 名称
    private MessageChannel messageChannel;

    @Autowired
    private Source source;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    @Qualifier(MessageSource.OUTPUT) // Bean 名称
    private MessageChannel gupaoMessageChannel;

    /**
     * 发送消息
     * @param message 消息内容
     */
    public void send(String message){
        // 通过消息管道发送消息
//        messageChannel.send(MessageBuilder.withPayload(message).build());
        source.output().send(MessageBuilder.withPayload(message).build());
    }

    /**
     * 发送消息到 Gupao
     * @param message 消息内容
     */
    public void sendToGupao(String message){
        // 通过消息管道发送消息
        gupaoMessageChannel.send(MessageBuilder.withPayload(message).build());
    }
}
```
#### 实现标准 `Sink` 监听
##### 绑定 `Sink`
```java
@Component
@EnableBinding({Sink.class})
public class MessageConsumerBean {

    @Autowired
    @Qualifier(Sink.INPUT) // Bean 名称
    private SubscribableChannel subscribableChannel;

    @Autowired
    private Sink sink;
    
}
```
##### 通过 `SubscribableChannel` 订阅消息
```java
    // 当字段注入完成后的回调
    @PostConstruct
    public void init(){
        // 实现异步回调
        subscribableChannel.subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println(message.getPayload());
            }
        });
    }
```
##### 通过 `@ServiceActivator` 订阅消
```java
    //通过@ServiceActivator
    @ServiceActivator(inputChannel = Sink.INPUT)
    public void onMessage(Object message) {
        System.out.println("@ServiceActivator : " + message);
    }
```
##### 通过 `@StreamListener` 订阅消息

```java
    @StreamListener(Sink.INPUT)
    public void onMessage(String message){
        System.out.println("@StreamListener : " + message);
    }
```
## Spring Cloud Stream Binder : [RabbitMQ](http://www.rabbitmq.com)
### 启动 RabbitMQ
## 重构工程，删除kafka 的强依赖的实现
## 问答部分
* `@EnableBinding` 有什么用？
  答：`@EnableBinding` 将 `Source`、`Sink` 以及 `Processor` 提升成相应的代理
* @Autorwired Source source这种写法是默认用官方的实现？
  答：是官方的实现
* 这么多消息框架 各自优点是什么 怎么选取
  答：RabbitMQ：AMQP、JMS 规范
  Kafka : 相对松散的消息队列协议
  ActiveMQ：AMQP、JMS 规范
  ​	AMQP v1.0 support
  ​	MQTT v3.1 support allowing for connections in an IoT environment.
  https://content.pivotal.io/rabbitmq/understanding-when-to-use-rabbitmq-or-apache-kafka
* 如果中间件如果有问题怎么办，我们只管用，不用维护吗。现在遇到的很多问题不是使用，而是维护，中间件一有问题，消息堵塞或丢失都傻眼了，都只有一键重启
  答：消息中间件无法保证不丢消息，多数高一致性的消息背后还是有持久化的。
* @EnableBinder， @EnableZuulProxy，@EnableDiscoverClient这些注解都是通过特定BeanPostProcessor实现的吗？
  答：不完全对，主要处理接口在`@Import`:
  * `ImportSelector` 实现类
  * `ImportBeanDefinitionRegistrar` 实现类
  * `@Configuration` 标注类
  * `BeanPostProcessor` 实现类
* 我对流式处理还是懵懵的 到底啥是流式处理 怎样才能称为流式处理 一般应用在什么场景？
  答：Stream 处理简单地说，异步处理，消息是一种处理方式。
  提交申请，机器生成，对于高密度提交任务，多数场景采用异步处理，Stream、Event-Driven。举例说明：审核流程，鉴别黄图。
* 如果是大量消息 怎么快速消费 用多线程吗？
  答：确实是使用多线程，不过不一定奏效。依赖于处理具体内容，比如：一个线程使用了
  25% CPU，四个线程就将CPU 耗尽。因此，并发 100个处理，实际上，还是 4个线程在处理。I/O 密集型、CPU 密集型。
* 如果是大量消息 怎么快速消费 用多线程吗
  答：大多数是多线程，其实也单线程，流式非阻塞。
* 购物车的价格计算可以使用流式计算来处理么？能说下思路么？有没有什么高性能的方式推荐？
  答：当商品添加到购物车的时候，就可以开始计算了。
  ​



