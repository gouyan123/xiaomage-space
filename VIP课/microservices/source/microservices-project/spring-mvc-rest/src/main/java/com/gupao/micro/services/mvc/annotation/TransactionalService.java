package com.gupao.micro.services.mvc.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Service // 它是 @Service 组件
@Transactional // 它是事务注解
public @interface TransactionalService { //  @Service + @Transactional

    @AliasFor(annotation = Service.class)
    String value(); // 服务名称

    @AliasFor(annotation = Transactional.class,attribute = "value")
    String txName();
}
