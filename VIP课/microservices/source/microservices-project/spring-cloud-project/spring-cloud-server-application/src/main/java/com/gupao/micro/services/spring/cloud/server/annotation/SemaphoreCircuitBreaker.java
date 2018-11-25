package com.gupao.micro.services.spring.cloud.server.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD) // 标注在方法
@Retention(RetentionPolicy.RUNTIME) // 运行时保存注解信息
@Documented
public @interface SemaphoreCircuitBreaker {

    /**
     * 信号量
     *
     * @return 设置超时时间
     */
    int value();

}
