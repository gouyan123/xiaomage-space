package com.gupao.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

public class ApplicationEventMulticasterDemo {

    public static void main(String[] args) {
        ApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();

        // 添加监听器
        multicaster.addApplicationListener(event -> {
            if (event instanceof PayloadApplicationEvent) {
                System.out.println("接受到 PayloadApplicationEvent :"
                        + PayloadApplicationEvent.class.cast(event).getPayload());
            }else {
                System.out.println("接收到事件：" + event);
            }
        });
        // 发布/广播事件
        multicaster.multicastEvent(new MyEvent("Hello,World"));
        multicaster.multicastEvent(new PayloadApplicationEvent<Object>("2", "Hello,World"));

    }

    private static class MyEvent extends ApplicationEvent {

        public MyEvent(Object source) {
            super(source);
        }
    }
}
