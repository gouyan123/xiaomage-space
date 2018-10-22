# Spring Boot JDBC



## Spring Boot 实际使用场景



> 题外话：在 Spring Boot 2.0.0 ，如果应用采用 Spring Web MVC 作为 Web 服务， 默认情况下，使用 嵌入式 Tomcat。
>
> 如果采用Spring Web Flux，默认情况下，使用 Netty Web Server（嵌入式）

> 从 Spring Boot 1.4 支持 FailureAnalysisReporter 实现

> WebFlux
>
> > Mono : 0 - 1 Publisher（类似于Java 8 中的 Optional）
> >
> > Flux:     0 - N Publisher（类似于Java 中的 List）
> >
> > 传统的 Servlet 采用 HttpServletRequest、HttpServletResponse
> >
> > WebFlux 采用：ServerRequest、ServerResponse（不再限制于 Servlet 容器，可以选择自定义实现，比如 Netty Web Server）



### 单数据源的场景



#### 数据连接池技术



##### [Apache Commons DBCP](http://commons.apache.org/proper/commons-dbcp/)

 *  commons-dbcp2 

     * 依赖：commons-pool2

* commons-dbcp（老版本）

  * 依赖：commons-pool

  ​

##### [Tomcat DBCP](http://tomcat.apache.org/tomcat-8.5-doc/jndi-datasource-examples-howto.html)



## 事务

### 重要感念



#### 自动提交模式

#### 事务隔离级别（Transaction isolation levels）

* TRANSACTION_READ_UNCOMMITTED
* TRANSACTION_READ_COMMITTED
* TRANSACTION_REPEATABLE_READ
* TRANSACTION_SERIALIZABLE

从上至下，级别越高，性能越差



Spring Transaction 实现重用了 JDBC API：

Isolation -> TransactionDefinition 

* ISOLATION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED
* ISOLATION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED
* ISOLATION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ
* ISOLATION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE

#### 保护点（Savepoints）



save(){

 // 建立一个SP 1

 SP 1

 SP 2 {

  // 操作

} catch(){

 rollback(SP2);

}

commit();

release(SP1);

}





#### @Transaction 

##### 代理执行 - `TransactionInterceptor`

 * 可以控制 rollback 的异常粒度：rollbackFor() 以及 noRollbackFor()
* 可以执行 事务管理器：transactionManager()



#### 通过 API 方式进行事务处理 - PlatformTransactionManager





线程 1:

调用 save ->

@Transactional T1 save() 控制 DS 1 insert -> 



 save2() DS 1 insert



@Transactional

save() {

  //insert DS1 

  save2() // insert DS1, 没有Transactional

}



@Transactional(NESTED)

save2(){

}





### 问题集合



#### 1. 用reactive web，原来mvc的好多东西都不能用了？

答：不是， Reactive Web 还是能够兼容 Spring WebMVC

#### 2. 开个线程池事务控制用API方式？比如开始写的Excutor.fixExcutor(5)

答：TransactionSynchronizationManager 使用大量的ThreadLocal 来实现的



#### 3. 假设一个service方法给了@Transaction标签，在这个方法中还有其他service 的某个方法，这个方法没有加@Transaction，那么如果内部方法报错，会回滚吗？

答：会的，当然可以过滤掉一些不关紧要的异常noRollbackFor()



#### 4. spring 分布式事务 生产环境实现方式有哪些?

答：https://docs.spring.io/spring-boot/docs/2.0.0.M5/reference/htmlsingle/#boot-features-jta

