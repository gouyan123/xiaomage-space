package com.gupao.micro.services.reactive;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {

        println("Hello,World 1");

        AtomicBoolean done = new AtomicBoolean(false);

        final  boolean isDone;

        // volatile 易变，线程安全（可见性）
        // final 不变，线程安全（一直不变）
        // final + volatile  = impossible

        Thread thread = new Thread(() -> {
            // 线程任务
            println("Hello,World 2018");
            // CAS
            done.set(true);  // 不通用
        });

        thread.setName("sub-thread");// 线程名字

        thread.start(); // 启动线程

        // 线程 join() 方法
        thread.join(); // 等待线程销毁

        println("Hello,World 2");

        // CountDownLatch
        // AQS -> 状态位，队列 Integer
    }

    private static void println(String message) {
        System.out.printf("[线程 : %s] %s\n",
                Thread.currentThread().getName(), // 当前线程名称
                message);
    }
}
