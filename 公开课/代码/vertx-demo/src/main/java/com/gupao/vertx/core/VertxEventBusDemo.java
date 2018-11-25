package com.gupao.vertx.core;

import io.vertx.core.Vertx;

/**
 * Vert.x Event Bus Demo
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/2/4
 */
public class VertxEventBusDemo {

    public static void main(String[] args) {
        // Reactor
        // RxJava 1 or 2
        // Vert.x Builder
        Vertx vertx = Vertx.vertx();

        demoEventBus(vertx);

        vertx.close();
    }

    /**
     * 演示 Verticle EventBus
     *
     * @param vertx
     */
    private static void demoEventBus(Vertx vertx) {

        String address = "test-address";
        // 事件订阅者（处理事件）

        // 消息通过地址区分，事件通过类型
        vertx.eventBus().consumer(address, message -> {
            // 处理消息（事件）
            Object payload = message.body();
            System.out.printf("Address : %s -> message : %s\n", address, payload);
        }).completionHandler(handler -> {
            System.out.println("消息消费结束");
        });

        // 事件发布者（发布事件）
        vertx.eventBus().publish(address,"Hello,World");
        vertx.eventBus().publish(address,12345);

    }
}
