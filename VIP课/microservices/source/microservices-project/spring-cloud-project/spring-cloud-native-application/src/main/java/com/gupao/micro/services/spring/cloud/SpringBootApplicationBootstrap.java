package com.gupao.micro.services.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
@SpringBootApplication
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
        // DispatcherServlet 是一个Servlet，那么Filter想用里面的bean怎么办？弄一个父Application作为通用bean交集
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
//    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.refresh();
//        context.setId("小马哥");    // 设置上下文名称
//        // 向小马哥上下文中 注册bean，beanName为"helloWorld"，然后当前上下文直接可以使用@Autowired String helloWorld 获取其bean了
//        context.registerBean("helloWorld",String.class,"HelloWorld");
//        new SpringApplicationBuilder(SpringBootApplicationBootstrap.class)
//                .parent(context)   // 显示设置双亲上下文
//                .run(args);
//    }
//    @Autowired
//    @Qualifier("helloWorld")
//    private String helloWorld;  // 使用父上下文小马哥 中的 bean
//
//    @RequestMapping(value = "/",method = RequestMethod.GET)
//    public String index(){
//        return helloWorld;
//    }
}
