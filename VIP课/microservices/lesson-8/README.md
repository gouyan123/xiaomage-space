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



### 一致性模型



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



* 错误配置（高 ZK Client 版本 3.5，低服务器版本 3.4）

```xml
    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
        </dependency>
    </dependencies>
```

* 正确配置

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

* 注册发现
* 配置管理



#### 启动 ZK（3.4.11）



#### 编写引导类

```java
@SpringBootApplication
@EnableDiscoveryClient // 尽可能使用 @EnableDiscoveryClient
public class ZkDSClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkDSClientApplication.class, args);
    }
}
```

实例一：端口 56517

ZK ID : fff20552-b4a0-43d8-a9ce-c82096f2005e



实例二：端口 56577

ZK ID : 9f4e2c91-4765-4f39-9d4b-2036e8e6c4d4



ZK 节点路径（/services/spring-cloud-service-discovery-client）



ZK 服务发现节点规则（/services/{spring.application.name}/{serviceId_UUID}/)



注册增加 service Id 计算

获取应该去重



```

```







Eureka 2.0 不开源，Eureka 1.x 还可以用的







## 下节预习

### Spring Cloud 负载均衡

回顾去年 VIP

[第四节 Spring Cloud Netflix Ribbon](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-4)

