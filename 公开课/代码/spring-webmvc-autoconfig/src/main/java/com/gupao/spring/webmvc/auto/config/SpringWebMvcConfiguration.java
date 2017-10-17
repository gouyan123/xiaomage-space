package com.gupao.spring.webmvc.auto.config;

import com.gupao.spring.webmvc.auto.annotation.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *  Spring Web MVC 配置 Bean
 * 广告资源位...
 *
 * @author mercyblitz
 * @date 2017-10-09
 **/
@Configuration
@ComponentScan(basePackages = "com.gupao.spring.webmvc.auto")
public class SpringWebMvcConfiguration {

    @ConditionalOnClass(String.class)
    @Bean("helloWorld")
    public String helloWorld(){
        return "helloWorld";
    }

}
