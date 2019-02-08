package com.gupao.micro.services.mvc.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Service // 说明该注解继承了@Service，拥有@Service的特性
@Transactional // 说明该注解继承了@Transactional，拥有@Transactional的特性
public @interface TransactionalService { //  @Service + @Transactional
    /**annotation = Service.class表示暴露Service注解的value属性*/
    @AliasFor(annotation = Service.class)
    String value(); /*暴露名称可以自定义即起别名，也可同名覆盖；没写 default表示不能为空null*/
    /**annotation = Transactional.class表示暴露Transactional注解的属性；attribute = "value"表示暴露value这个属性*/
    @AliasFor(annotation = Transactional.class,attribute = "value")
    String txName();/*暴露名称可以自定义即起别名，也可同名覆盖；没写 default表示不能为空null*/
}
