# Spring Cloud Netflix Eureka



## Spring Cloud Netflix Eureka



## 传统的服务治理



### 通讯协议

XML-RPC -> XML 方法描述、方法参数 -> WSDL（WebServices 定义语言）

WebServices -> SOAP（HTTP、SMTP） -> 文本协议（头部分、体部分）

REST -> JSON/XML( Schema ：类型、结构) -> 文本协议（HTTP Header、Body）

W3C Schema ：xsd:string 原子类型，自定义自由组合原子类型

Java POJO : int、String

Response Header -> Content-Type: application/json;charset=UTF-8

Dubbo：Hession、 Java Serialization（二进制），跨语言不变，一般通过 Client（Java、C++）

> 二进制的性能是非常好（字节流，免去字符流（字符编码），免去了字符解释，机器友好、对人不友好）

> 序列化：把编程语言数据结构转换成字节流、反序列化：字节流转换成编程语言的数据结构（原生类型的组合）



## 高可用架构





URI：统一资源定位符

http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP课/spring-cloud/lesson-3

URI：用于网络资源定位的描述 Universal Resource Identifier

URL: Universal Resource Locator

网络是通讯方式

资源是需要消费媒介

定位是路由



Proxy：一般性代理，路由

​	Nginx：反向代理	

Broker：包括路由，并且管理，老的称谓（MOM）

​	Message Broker：消息路由、消息管理（消息是否可达）



### 可用性比率计算

可用性比率：通过时间来计算（一年或者一月）

比如：一年 99.99 % 

可用时间：365 * 24  * 3600 * 99.99% 

不可用时间：365 * 24  * 3600 * 0.01% = 3153.6 秒 < 一个小时

不可以时间：1个小时 推算一年 1 / 24 / 365 = 0.01 %



单台机器不可用比率：1%

两台机器不可用比率：1% * 1%

N 机器不可用比率：1% ^ n



### 可靠性

微服务里面的问题：

一次调用：

   A ->       B    ->  C

99% -> 99% -> 99% = 97%

   A ->     B    ->  C -> D

99% -> 99% -> 99%  -> 99% = 96%



结论：增加机器可以提高可用性，增加服务调用会降低可靠性，同时降低了可用性







## Eureka 客户端



## Eureka 服务器



Eureka 服务器一般不需要自我注册，也不需要注册其他服务器



Eureka  自我注册的问题，服务器本身没有启动



> Fast Fail : 快速失败
>
> Fault-Tolerance ：容错



通常经验，Eureka 服务器不需要开启自动注册，也不需要检索服务

```properties
### 取消服务器自我注册
eureka.client.register-with-eureka=false
### 注册中心的服务器，没有必要再去检索服务
eureka.client.fetch-registry = false
```

但是这两个设置并不是影响作为服务器的使用，不过建议关闭，为了减少不必要的异常堆栈，减少错误的干扰（比如：系统异常和业务异常）



 **Replicas**





## 问答部分

1. consul 和 Eureka 是一样的吗

   答：提供功能类似，consul 功能更强大，广播式服务发现/注册

2. 重启eureka 服务器，客户端应用要重启吗

   答：不用，客户端在不停地上报信息，不过在 Eureka 服务器启动过程中，客户单大量报错

3. 生产环境中，consumer是分别注册成多个服务，还是统一放在一起注册成一个服务？权限应该如何处理？

   答：consumer 是否要分为多个服务，要情况，大多数情况是需要，根据应用职责划分。权限根据服务方法需要，比如有些敏感操作的话，可以更具不同用户做鉴权。

4. 客户端上报的信息存储在哪里？内存中还是数据库中

   答：都是在内存里面缓存着，EurekaClient 并不是所有的服务，需要的服务。比如：Eureka Server 管理了 200个应用，每个应用存在 100个实例，总体管理 20000 个实例。客户端更具自己的需要的应用实例。

5. 要是其他模块查询列表里面 有用到用户信息怎么办呢 是循环调用户接口 还是直接关联用户表呢 怎么实现好呢

   答：用户 API 依赖即可

6.  consumer 调用 Aprovider-a 挂了，会自动切换 Aprovider-b吗，保证请求可用吗

   答：当 Aprovider-a 挂，会自动切换，不过不一定及时。不及时，服务端可能存在脏数据，或者轮训更新时间未达。

7. 一个业务中调用多个service时如何保证事务

   答：需要分布式事务实现（JTA），可是一般互联网项目，没有这种昂贵的操作。

   ​