package com.gupao.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Vert.x Demo
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/2/4
 */
public class VertxDemo {

    public static void main(String[] args) {
        // Reactor
        // RxJava 1 or 2
        // Vert.x Builder
        Vertx vertx = Vertx.vertx();

        demoVerticle(vertx);

        vertx.close();
    }

    /**
     * 演示 Verticle
     *
     * @param vertx
     */
    private static void demoVerticle(Vertx vertx) {

        vertx.deployVerticle(new AbstractVerticle() {
            @Override
            public void start(Future<Void> startFuture) throws Exception {
                System.out.println("Start");
                startFuture.complete();
            }

            @Override
            public void stop() throws Exception {
                System.out.println("Stop");
            }
        });


    }

    private static void demoSetPeriodic(Vertx vertx) {
        // 实现定时器
        // 500 毫秒 执行打印
        vertx.setPeriodic(500, System.out::println);
        vertx.setPeriodic(500, System.out::println);
        vertx.setPeriodic(500, System.out::println);
        // 1000 毫秒 执行打印
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(() -> System.out.println("Hello,World"),
                        1000, TimeUnit.MILLISECONDS);
        executorService.shutdown();
    }
}
