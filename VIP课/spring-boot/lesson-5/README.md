# Spring Boot Bean Validator



## Bean Validation 1.1 JSR-303



### Maven 依赖



```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

> 命名规则（Since Spring Boot 1.4）：Spring Boot 大多数情况采用 `starter`(启动器，包含一些自动装配的Spring 组件），官方的命名规则：`spring-boot-starter-{name}`，业界或者民间：`{name}-spring-boot-starter`



## 常用验证技术

### Spring Assert API

### JVM/Java assert 断言



以上方式的缺点，耦合了业务逻辑，虽然可以通过`HandlerInterceptor` 或者`Filter`做拦截，但是也是非常恶心的

还可以通过 AOP 的方式，也可以提升代码的可读性。



以上方法都有一个问题，不是统一的标准。



## 自定义 Bean Validation

需求：通过员工的卡号来校验，需要通过工号的前缀和后缀来判断

前缀必须以"GUPAO-"

后缀必须是数字

需要通过 Bean Validator 检验



### 实现步骤

1. 复制成熟 Bean Validation Annotation的模式

   ```java
   @Target(FIELD)
   @Retention(RUNTIME)
   @Documented
   @Constraint(validatedBy = {})
   public @interface ValidCardNumber {
   }
   ```

   ​

2.  参考和理解`@Constraint`

3. 实现`ConstraintValidator` 接口

4. 将实现`ConstraintValidator` 接口 定义到`@Constraint#validatedBy`

5. 给`@ValidCardNumber` 添加 `message` 参数







## 问答部分

1. JSON校验如何办？

   答：尝试变成 Bean 的方式

2. 实际中 很多参数都要校验 那时候怎么写 这样写会增加很多类

   答：确实会增加部分工作量，大多数场景，不需要自定义，除非很特殊情况。Bean Validation 的主要缺点，单元测试不方便

3. 如果前端固定表单的话，这种校验方式很好。但是灵活性不够，如果表单是动态的话，如何校验呢？

   答： 表单字段与 Form 对象绑定即可，再走 Bean Validation 逻辑

   ```html
   <form action="" method="POST" command="form">
     <input value="${form.name}" />
     ...
     <input value="${form.age}" />
    
   </form>
   ```

   一个接一个验证，责任链模式（Pipeline）：

   field 1-> field 2 -> field 3 -> compute -> result

4. 如何自定义，反回格式？如何最佳实现

   答：可以通过REST来实现，比如 XML 或者 JSON 的格式（视图）

5. 面试的看法

   答：具备一定的水平

   不该问的不要问，因为面试官的水平可能还不及于你！

