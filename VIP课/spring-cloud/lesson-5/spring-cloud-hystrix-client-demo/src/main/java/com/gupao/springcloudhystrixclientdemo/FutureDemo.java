package com.gupao.springcloudhystrixclientdemo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Future Demo
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/1
 */
public class FutureDemo {

    public static void main(String[] args) {

        Random random = new Random();

        ExecutorService service = Executors.newFixedThreadPool(1);

        Future<String> future = service.submit(() -> { // 正常流程
            // 如果随机时间 大于 100 ，那么触发容错
            int value = random.nextInt(200);

            System.out.println("helloWorld() costs " + value + " ms.");

            Thread.sleep(value);

            return "Hello,World";
        });

        try {
            future.get(100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // 超时流程
            System.out.println("超时保护！");
        }

        service.shutdown();
    }
}
