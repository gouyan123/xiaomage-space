package com.gupao.micro.services.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
public class SpringBootApplicationBootstrap {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
        parentContext.setId("小马哥");
        // 在"小马哥" 上下文注册一个 "helloWorld" String 类型的 Bean
        parentContext.registerBean("helloWorld", String.class, "Hello,World");
        // 启动"小马哥" 上下文
        parentContext.refresh();


        // 类比于 Spring WebMVC，Root WebApplication 和 DispatcherServlet WebApplication
        // DispatcherServlet WebApplication parent = Root WebApplication
        // DispatcherServlet Servlet
        // Filter -> Root WebApplication

        new SpringApplicationBuilder(SpringBootApplicationBootstrap.class)
                .parent(parentContext) // 显式地设置双亲上下文
                .run(args);
    }

    @Autowired // String message Bean
    @Qualifier("helloWorld") // Bean 名称，来自于 “小马哥” 上下文
    private String message;

    @RequestMapping("")
    public String index() {
        return message;
    }

}
