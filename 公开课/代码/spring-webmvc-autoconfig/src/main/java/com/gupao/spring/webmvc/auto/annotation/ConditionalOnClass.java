package com.gupao.spring.webmvc.auto.annotation;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * TODO:小马哥，写点注释吧！
 * 广告资源位...
 *
 * @author mercyblitz
 * @date 2017-10-09
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnClassCondition.class)
public @interface ConditionalOnClass {

    Class<?>[] value();

}
