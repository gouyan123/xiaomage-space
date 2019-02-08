package com.gupao.micro.services.mvc.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(method = RequestMethod.OPTIONS)  // 如果不增加元注解的话，会报错
public @interface OptionsMapping {
    //@OptionsMapping要表示@RequestMapping的属性，必须自己重新该定义属性，才能表示
    @AliasFor(annotation = RequestMapping.class) // 指定之后变成RequestMethod的属性
    String name() default ""; // 不加的话，只是代表@OptionsMapping自己的属性
}
