package com.gupao.microservicesproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class MicroservicesProjectApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MicroservicesProjectApplication.class);
        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("server.port", 0);
        springApplication.setDefaultProperties(properties);
        // 设置为 非 web 应用
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext context = springApplication.run(args);
        // 有异常？
        System.out.println(context.getBean(MicroservicesProjectApplication.class));
        // 输出当前 Spring Boot 应用的 ApplicationContext 的类名
        System.out.println("当前 Spring 应用上下文的类：" + context.getClass().getName());
    }
}
