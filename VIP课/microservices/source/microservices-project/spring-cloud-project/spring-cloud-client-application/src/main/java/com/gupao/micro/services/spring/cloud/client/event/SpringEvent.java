package com.gupao.micro.services.spring.cloud.client.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.annotation.Annotation;

public class SpringEvent {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(Object.class);

        // 增加 监听
        context.addApplicationListener(e -> {
            System.err.println("监听 : " + e.getClass().getSimpleName());
        });

        context.refresh();
        context.start();
        context.stop();
        context.close();

    }
}
