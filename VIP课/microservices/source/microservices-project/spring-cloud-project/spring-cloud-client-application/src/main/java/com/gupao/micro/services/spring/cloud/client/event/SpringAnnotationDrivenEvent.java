package com.gupao.micro.services.spring.cloud.client.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;

public class SpringAnnotationDrivenEvent {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // SpringAnnotationDrivenEvent 注册为 Spring Bean
        context.register(SpringAnnotationDrivenEvent.class);

        context.refresh(); // 启动上下文
        // 确保上下文启动完毕后，再发送事件
        context.publishEvent(new MyApplicationEvent("Hello,World"));

        context.close(); // 关闭上下文

    }

    private static class MyApplicationEvent extends ApplicationEvent {

        /**
         * Create a new ApplicationEvent.
         *
         * @param source the object on which the event initially occurred (never {@code null})
         */
        public MyApplicationEvent(Object source) {
            super(source);
        }
    }

//    @EventListener
//    public void onMessage(MyApplicationEvent event) {
//        System.err.println("监听到MyApplicationEvent 事件源 : " + event.getSource());
//    }

    @EventListener
    public void onMessage(Object eventSource) {
        System.err.println("监听到 MyApplicationEvent 事件源 : " + eventSource);
    }
}
