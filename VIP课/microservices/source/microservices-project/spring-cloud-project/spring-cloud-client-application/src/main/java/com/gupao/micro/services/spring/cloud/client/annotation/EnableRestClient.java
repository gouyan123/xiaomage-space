package com.gupao.micro.services.spring.cloud.client.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RestClientsRegistrar.class)
public @interface EnableRestClient {

    /**
     * 指定 @RestClient 接口
     * @return
     */
    Class<?>[] clients() default {};
}
