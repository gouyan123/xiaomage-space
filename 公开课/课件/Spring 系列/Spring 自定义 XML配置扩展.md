DOM = Document Object Model - Tree

​	属性结构，好理解，性能最差



SAX -> Simple API for XML 

​	文本处理，性能好



XML Stream -> BEA ( Event )

​	时间处理，性能良好，相对好理解



JAXB ->  Java API XML Binding

​	面向对象，容易OOP，性能良好







Spring < 2.0 DTD

​	log4j.dtd

​	spring.dtd

​	mybatis



Spring >= 2.0 Schema

​	spring-beans.xsd

​	spring-context.xsd





Schema -> Java API XML Binding (JAXB)

SOAP (Simple Object Access Protocol) -> WSDL



* org.springframework.beans.factory.config.PlaceholderConfigurerSupport
  * org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
  * org.springframework.context.support.PropertySourcesPlaceholderConfigurer



Spring 3

Spring 4

Spring 5



Spring XML 扩展



Schema 配置

​	`META-INF/spring.schemas`

​	schema 绝对路径 = schema 相对路径 

​	`Properties`

```properties
key = value
```

```properties
key:value
```

schema 定义 namespace -> 处理类是谁？

```
http://www.springframework.org/schema/context/spring-context.xsd
```

```
http://www.springframework.org/schema/context
```



<dubbo:reference />



Namespace Handler 配置

​	`META-INF/spring.handlers`

```
http://www.springframework.org/schema/context
```

-> Handler 配置

```
org.springframework.context.config.ContextNamespaceHandler
```



Schema 绝对路径

```
http://www.springframework.org/schema/context/spring-context.xsd
```

Schema 相对路径

```
org/springframework/context/config/spring-context.xsd
```

`spring-context.xsd` 定义命名空间 `targetNamespace="http://www.springframework.org/schema/context"`

Schema 命名空间

```
http://www.springframework.org/schema/context
```

Handler 类

```
org.springframework.context.config.ContextNamespaceHandler
```

Locale Element name 映射 `BeanDefinitionParser`



```
"property-placeholder" => PropertyPlaceholderBeanDefinitionParser
```



Bean 定义 ： `BeanDefinition`

Bean 定义解析器：`BeanDefinitionParser`



Spring XML Schema -> 自定义元素 -> 解析 Bean 定义



* 定义 Schema - `gubao.xsd`

  * 定义元素 user -> `User`
  * 定义 targetNamespace = http://gupaoedu.com/schema/gupao

* 建立 Schema 绝对路径和相对路径映射 - `META-INF/spring.schemas`

  * 绝对的 XSD = 相对的  XSD

  * ```properties
    http\://gupaoedu.com/schema/gupao.xsd = gupao.xsd
    ```

* 添加 `gupao.xsd` 到 `context.xml`

  * 引入 namespace 
    * `xmlns:gupao="http://gupaoedu.com/schema/gupao"`
  * 配置 namesapce Schema 路径
    * `http://gupaoedu.com/schema/gupao http://gupaoedu.com/schema/gupao.xsd
  * 引入 <`gupao:user` />

* 建立 Schema namespace 与 Handler 映射 - `META-INF/spring.handlers`

  * 实现 `NamespaceHandledr` 或 扩展 `NamespaceHandlerSupport` - `GupaoNamespaceHandler`
  * 实现 `BeanDefinitionParser` 接口，创建 "user" 元素的 `BeanDefintionParser` 实现
  * 建立 namespace 与 `BeanDefintionParser` 映射
    * `http\://gupaoedu.com/schema/gupao =com.gupao.springxmlextension.context.config.GupaoNamespaceHandler`





`BeanDefinitionRegistry` `BeanFactory` =  `DefaultListableBeanFactory` => `XmlBeanFactory`



JDBC Session -> Connect



JMS Session -> ConnectionFactory -> Connection



Visitor ->

XML Stream

```xml
<beans> -- start tag
    -- start tag
	<gupao:user bean-name="user2" id="2" name="小马哥" /> -> Element localname = user
</beans> -- end tag
```

`` -> `Document` -> Root Element -> `<beans>`

`EncodedResource `(`context.xml`)

​	-> BeanDefinition

* beans
  * gupao:user
  * gupao:user



ApplicationContext

* AbstractApplicationContext
   * AbstractXmlApplicationContext
      * ClassPathXmlApplicationContext



ClassPathXmlApplicationContext -> obtainFreshBeanFactory() from refresh()

​	-> AbstractXmlApplicationContext

​		-> XmlBeanDefinitionReader

​			-> BeanDefinitionParserDelegate

​				-> NamespaceHandler

​					-> BeanDefinitionParser



XML 资源 Resource

XML Dom 解析

schemas、handlers 映射

Element -> BeanDefinition 

BeanDefinitionParser



AbstractApplicationContext

​	-> XML

​		资源 XML

​	-> Annotation

​		Class

​	-> Groovy

​		Groovy Class



=> BeanDefinitions => Spring Bean 生命周期的处理