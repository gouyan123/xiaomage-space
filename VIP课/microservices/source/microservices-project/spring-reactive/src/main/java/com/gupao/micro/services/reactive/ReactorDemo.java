package com.gupao.micro.services.reactive;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.Stream;

public class ReactorDemo {
    public static void main(String[] args) {
        Flux.just(0,1,2,3,4,5,6,7,8,9)              //Flux是一个 reactor
                .filter(v -> v % 2 == 1)
                .map(v -> v - 1)
                .reduce(Integer::sum)
                .subscribeOn(Schedulers.elastic())  //main线程里面 新启一个线程 运行观察者，观察者还没开始运行，main线程就结束了，观察者线程也跟着结束了
                .subscribe(ReactorDemo::println);   //订阅才执行
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) throws InterruptedException {
//
////        Flux.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) // 直接执行
////                .filter(v -> v % 2 == 1) // 判断数值->获取奇数
////                .map(v -> v - 1) // 奇数变偶数
////                .reduce(Integer::sum) // 聚合操作
////                .subscribeOn(Schedulers.elastic())
//////                .subscribeOn(Schedulers.parallel())
//////                .block());
////                .subscribe(ReactorDemo::println) // 订阅才执行
////        ;
////        Thread.sleep(1000);
//
//        Stream
//                .of(1, 2, 3, 4, 5)                         // 生产
//                .map(String::valueOf)               // 处理
//                .forEach(System.out::println);  // 消费
//
//        System.exit(128);
//        // for-each 必须实现 java.lang.Iterable
//        // []、Collection
//
//    }

    private static void println(Object message) {
        System.out.printf("[线程 : %s] %s\n",
                Thread.currentThread().getName(), // 当前线程名称
                message);
    }

}
