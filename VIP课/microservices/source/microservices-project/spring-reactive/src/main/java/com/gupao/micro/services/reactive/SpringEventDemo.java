package com.gupao.micro.services.reactive;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.GenericApplicationContext;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpringEventDemo {

    public static void main(String[] args) {

        // 默认是同步非阻塞
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        // 构建线程池
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // 切换成异步非阻塞
        multicaster.setTaskExecutor(executor);

        // 增加事件监听器
        multicaster.addApplicationListener(event -> { // Lambda 表达
            // 事件监听
            System.out.printf("[线程 : %s] event : %s\n",
                    Thread.currentThread().getName(), // 当前执行线程名称
                    event);
        });

        // 广播事件
        multicaster.multicastEvent(new PayloadApplicationEvent("Hello,World", "Hello,World"));
        multicaster.multicastEvent(new PayloadApplicationEvent("Hello,World", "Hello,World"));
        multicaster.multicastEvent(new PayloadApplicationEvent("Hello,World", "Hello,World"));

        // 关闭线程池
        executor.shutdown();
    }
}
