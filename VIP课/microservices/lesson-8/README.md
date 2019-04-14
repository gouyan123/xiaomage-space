# 第八节 Spring Cloud 服务发现
## 简单对比 Eureka
| 比较点        | Eureka                                                  | Zookeeper                    | Consul                    |
| ------------- | ------------------------------------------------------- | ---------------------------- | ------------------------- |
| 运维熟悉度    | 相对陌生                                                | 熟悉                         | 更陌生                    |
| 一致性（CAP） | AP（最终一致性）                                        | CP（一致性强）               | AP（最终一致性）          |
| 一致性协议    | HTTP 定时轮训                                           | ZAB                          | RAFT                      |
| 通讯方式      | HTTP REST                                               | 自定义协议                   | HTTP REST                 |
| 更新机制      | Peer 2 Peer（服务器之间） + Scheduler（服务器和客户端） | ZK Watch                     | Agent 监听的方式          |
| 适用规模      | 20 K ~ 30 K 实例（节点）                                | 10K ~ 20K 实例（节点）       | < 3K 实例（节点）         |
| 性能问题      | 简单的更新机制、复杂设计、规模较大时 GC 频繁            | 扩容麻烦、规模较大时 GC 频繁 | 3K 节点以上，更新列表缓慢 |

## 为什么推荐使用 ZK 作为 Spring Cloud 的基础设施

### 一致性模型：CP
### 维护相对熟悉
### 配置中心和服务注册中心单一化

##### 传统的问题

Spring Cloud 默认配置
Eureka 做注册中心
Git/JDBC 做配置中心

## 主要内容
### Spring Cloud Discovery 客户端
### Spring Cloud Discovery ZK 服务器
#### Spring Cloud 增加 ZK 依赖

##实例
###1、启动zookeeper服务端，作为注册中心：zkServer.cmd
###2、将 微服务spring-cloud-service-discovery-client的实例  注册到 zookeeper：
①依赖zookeeper 依赖spring-cloud-starter-zookeeper-all
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zookeeper-all</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.apache.zookeeper</groupId>
    <artifactId>zookeeper</artifactId>
    <version>3.4.12</version>
    <exclusions>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```
②application.properties
```properties
spring.application.name = spring-cloud-service-discovery-client #服务名
server.port = 0                                                 #0表示随机服务端口
management.server.port = 7071                                   #管理端点 endpoints的端口；访问管理端点 http://localhost:7071/actuator
management.endpoints.web.exposure.include = *                   #开放 所有Web 管理 Endpoints
spring.cloud.zookeeper.connect-string=localhost:2181            #将微服务实例信息注册到zk，并从zk获取所有其他实例信息
```
③启动类加 @EnableDiscoveryClient注解
```java
@SpringBootApplication
@EnableDiscoveryClient // 尽可能使用 @EnableDiscoveryClient
public class ZkDSClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZkDSClientApplication.class, args);
    }
}
```
④启动zk客户端，查看注册的微服务实例信息：
> zkCli.cmd
> ls /services/微服务名称，返回 cbf3f6e1-4079-4004-b2c1-041f34a31256
> get /services/spring-cloud-service-discovery-client/cbf3f6e1-4079-4004-b2c1-041f34a31256，返回 实例信息

⑤注意事项：注册增加 service Id 计算
获取应该去重

##Nacos注册配置中心
1、注册实例信息：将实例信息 注册到 eureka上；
2、拉取实例信息：定时将eureka上实例信息同步到 服务元信息网关，去该 服务元信息网关拉取实例实现，该 服务元信息网关可以将eureka中数据缓存在redis中，并
持久化到DB中；
Eureka 2.0 不开源，Eureka 1.x 还可以用的
## 下节预习

### Spring Cloud 负载均衡

回顾去年 VIP

[第四节 Spring Cloud Netflix Ribbon](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-4)

