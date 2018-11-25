package com.gupao.micro.services.reactive;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.Stream;

public class ReactorDemo {

    public static void main(String[] args) throws InterruptedException {

//        Flux.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) // 直接执行
//                .filter(v -> v % 2 == 1) // 判断数值->获取奇数
//                .map(v -> v - 1) // 奇数变偶数
//                .reduce(Integer::sum) // 聚合操作
//                .subscribeOn(Schedulers.elastic())
////                .subscribeOn(Schedulers.parallel())
////                .block());
//                .subscribe(ReactorDemo::println) // 订阅才执行
//        ;
//        Thread.sleep(1000);

        Stream
                .of(1, 2, 3, 4, 5)                         // 生产
                .map(String::valueOf)               // 处理
                .forEach(System.out::println);  // 消费

        System.exit(128);
        // for-each 必须实现 java.lang.Iterable
        // []、Collection

    }

    private static void println(Object message) {
        System.out.printf("[线程 : %s] %s\n",
                Thread.currentThread().getName(), // 当前线程名称
                message);
    }

}
