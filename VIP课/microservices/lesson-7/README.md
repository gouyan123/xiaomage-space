# 第七节 Spring Cloud 配置管理



## 分布式配置



### 国内知名开源项目

百度 Disconf

携程 Apollo

阿里 Nacos



### 国外知名开源项目

Spring Cloud Config

Netfix [Archaius](http://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html#_external_configuration_archaius) 

Apache Zookeeper







### 客户端



#### 配置三方库



#####  `commons-configuration`

* `Configuration` : 提供大多数常见类型的 Value 转换

  * `PropertiesConfiguration`: 将 Properties 作为 `Configuration` 配置

  * `MapConfiguration`

    * `EnvironmentConfiguration` ： OS 环境变量
    * `SystemConfiguration` : Java 系统属性

  * `CompositeConfiguration`

    



核心概念：配置源、以及它们优先次序、配置转换能力



HTTP 资源算不算一个配置？

配置源：文件、HTTP 资源、数据源、 Git ->

URL -> file:/// , http://, jdbc:// , git://



##### Spring Environment



```sequence
Environment -> ConfigurableEnvironment: 父子层次
ConfigurableEnvironment -> MutablePropertySources: 获取可变多个配置源
MutablePropertySources -> List PropertySource : 包含多个 PropertySource
```

`PropertySource` : 配置源

* `MapPropertySource`
  * `PropertiesPropertySource`
* `CompositePropertySource` : 组合
* `SystemEnvironmentPropertySource` 环境变量





Spring Cloud 客户端配置定位扩展 : `PropertySourceLocator`







### 服务端



#### 基于 Git 实现

版本化配置

/应用名/profile/${label}

/应用名/profile/ = /应用名/profile/master

/应用名/ = /应用名.properties

${label} : 分支



Spring Cloud Config 实现一套完整的配置管理 API 设计



Git 实现缺陷：

* 复杂的版本更新机制（ Git 仓库）
  * 版本
  * 分支
  * 提交
  * 配置
* 憋足的内容更新（实时性不高）
  * 客户端第一次启动拉取
  * 需要整合 BUS 做更新通知



#### 设计原理



##### 分析 `@EnableConfigServer`

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ConfigServerConfiguration.class)
public @interface EnableConfigServer {

}
```

实际配置类 `ConfigServerConfiguration`

```java
@Configuration
public class ConfigServerConfiguration {
	class Marker {}

	@Bean
	public Marker enableConfigServerMarker() {
		return new Marker();
	}
}
```



```java
@Configuration
@ConditionalOnBean(ConfigServerConfiguration.Marker.class)
@EnableConfigurationProperties(ConfigServerProperties.class)
@Import({ EnvironmentRepositoryConfiguration.class, CompositeConfiguration.class, ResourceRepositoryConfiguration.class,
		ConfigServerEncryptionConfiguration.class, ConfigServerMvcConfiguration.class })
public class ConfigServerAutoConfiguration {

}
```

当应用配置类标注了

*  `@EnableConfigSever` 
  * 导入 `ConfigServerConfiguration`
    * 注册 `Marker` Bean
      * 作为 `ConfigServerAutoConfiguration` 条件之一



##### 案例分析 JDBC 实现



* JdbcTemplate Bean 来源

  * `JdbcTemplateAutoConfiguration`

* SQL 来源

  * `JdbcEnvironmentProperties`

    * `spring.cloud.config.server.jdbc.sql` 

      * 不配置，默认：`DEFAULT_SQL`

        * ```sql
          SELECT KEY, VALUE from PROPERTIES where APPLICATION=? and PROFILE=? and LABEL=?
          ```

| KEY  | VALUE      | APPLICATION | PROFILE | LABEL  |
| ---- | ---------- | ----------- | ------- | ------ |
| name | mercyblitz | config      | default | master |
| name | xiaomage   | config      | test    | master |
|      |            |             |         |        |

本质说明：

JDBC 连接技术

DB 存储介质

`EnvironmentRepository` 核心接口



思考是否可以自定义 `EnvironmentRepository`  实现？

前提：如何激活自定义的 `EnvironmentRepository`  实现

找到了为什么默认是 Git 作为配置仓库的原因：

```java
@Configuration
@ConditionalOnMissingBean(value = EnvironmentRepository.class, search = SearchStrategy.CURRENT)
class DefaultRepositoryConfiguration {
	...
	@Bean
	public MultipleJGitEnvironmentRepository defaultEnvironmentRepository(
	        MultipleJGitEnvironmentRepositoryFactory gitEnvironmentRepositoryFactory,
			MultipleJGitEnvironmentProperties environmentProperties) throws Exception {
		return gitEnvironmentRepositoryFactory.build(environmentProperties);
	}
}
```

当 Spring 应用上下文没有出现 `EnvironmentRepository` Bean 的时候，那么，默认激活 `DefaultRepositoryConfiguration` (Git 实现)，否则采用自定义实现。





#### 自定义实现



自定义 `EnvironmentRepository` Bean

```java
    @Bean
    public EnvironmentRepository environmentRepository() {
        return (String application, String profile, String label) -> {
            Environment environment = new Environment("default", profile);
            List<PropertySource> propertySources = environment.getPropertySources();
            Map<String, Object> source = new HashMap<>();
            source.put("name", "小马哥");
            PropertySource propertySource = new PropertySource("map", source);
            // 追加 PropertySource
            propertySources.add(propertySource);
            return environment;
        };
    }
```

以上实现将失效 `DefaultRepositoryConfiguration` 装配。





#### HTTP 请求模式



/${application}/$${profile}/$${label}



`@Controller` 或者 `@RestController`



@RequestMapping("/{application}/{profile}/{label}")





/config/test/master

config : application

test : profile

master : label











#### 比较 Spring Cloud 内建配置仓储的实现

Git 早放弃

JDBC 太简单

Zookeeper 比较适合做分布式配置

自定义是高端玩家



## 下节预习

### Spring Cloud 服务发现

回顾去年 VIP

[第三节 Spring Cloud Netflix Eureka](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-3)

