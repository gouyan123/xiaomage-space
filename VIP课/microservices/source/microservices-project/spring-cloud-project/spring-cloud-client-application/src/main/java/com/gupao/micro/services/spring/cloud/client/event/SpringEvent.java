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
        /*调用被监听方法，被监听方法里面调用监听器的监听方法，监听方法参数是事件*/
        /*refresh()刷新上下文*/
        context.refresh();
        /*start()方法会把上下文变成播放状态，即isActive() = true*/
        context.start();
        /*stop()方法会把上下文变成暂停状态，即isActive() = false*/
        context.stop();
        /*close()方法会把上下文变成关闭状态，即isRunning() = false*/
        context.close();

    }
}
