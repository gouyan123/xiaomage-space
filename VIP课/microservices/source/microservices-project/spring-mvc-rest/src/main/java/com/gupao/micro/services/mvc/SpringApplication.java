package com.gupao.micro.services.mvc;

import com.gupao.micro.services.mvc.service.EchoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.DefaultTransactionStatus;

@ComponentScan(basePackages = "com.gupao.micro.services.mvc.service")
@EnableTransactionManagement
public class SpringApplication {

    /**定义事务*/
    @Component("myTxName")  // myTxName改为其他名字会报错
    public static class MyPlatformTransactionManager implements PlatformTransactionManager {
        @Override
        public TransactionStatus getTransaction(@Nullable TransactionDefinition definition) throws TransactionException {
            return new DefaultTransactionStatus(
                    null, true, true,
                    definition.isReadOnly(), true, null
            );
        }

        @Override
        public void commit(TransactionStatus status) throws TransactionException {
            System.out.println("Commit()....");
        }

        @Override
        public void rollback(TransactionStatus status) throws TransactionException {
            System.out.println("rollback()....");
        }
    }

    public static void main(String[] args) {
        /*AnnotationConfigApplicationContext存 通过@Componet即派生注解注释的类的 Bean*/
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册 SpringApplication 扫描  com.gupao.micro.services.mvc.service
        context.register(SpringApplication.class);
        context.refresh(); // 启动
        /*相当于 map.forEach()*/
        context.getBeansOfType(EchoService.class).forEach((beanName, bean) -> {
            System.err.println("Bean Name : " + beanName + " , Bean : " + bean);

            bean.echo("Hello,World");
        });
        context.close(); // 关闭
    }
}
