package com.gupao.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAnnotationDemo {

    public static void main(String[] args) {

        // XML配置文件驱动上下文 ClassPathXmlApplicationContext
        // Annotation注解驱动上下文
        // 两者都是找 BeanDefinition的，即@Bean或者@Configuration注释的类的；
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册一个 Configuration Class = SpringAnnotationDemo
        context.register(SpringAnnotationDemo.class);
        // 上下文启动
        context.refresh();

        System.out.println(context.getBean(SpringAnnotationDemo.class));

    }
}
