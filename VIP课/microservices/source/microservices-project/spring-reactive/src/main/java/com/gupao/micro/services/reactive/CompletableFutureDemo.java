package com.gupao.micro.services.reactive;

import io.netty.util.concurrent.SingleThreadEventExecutor;

import java.util.concurrent.*;

public class CompletableFutureDemo {

    public static void main(String[] args) {
        println("当前线程");
        CompletableFuture.supplyAsync(() -> {
           println("第一步");
           return "Hello";                          //第一步返回 Hello
        }).thenApplyAsync(result -> {
            println("第二步");
            return result + " World";               //第二步以第一步返回值 为输入
        }).thenAccept(CompletableFutureDemo::println).
        whenComplete((v,error) -> {
            println("执行结束");
        }).
        join();                                    //等待执行结束
        try {

        }catch (Exception e){

        }finally {

        }
    }
    private static void println(String message) {
        System.out.printf("[线程 : %s] %s\n",Thread.currentThread().getName(), message);
    }

//    public static void main(String[] args) {
//
//        println("当前线程");
//
//        // Reactive programming
//        // Fluent 流畅的
//        // Streams 流式的
//        // 流程编排
//        // 大多数业务逻辑是数据操作
//        // 消费类型  Consumer
//        // 转换类型  Function
//        // 提升/减少维度 map/reduce/flatMap
//        CompletableFuture.supplyAsync(() -> {
//            println("第一步返回 \"Hello\"");
//            return "Hello";
//        }).thenApplyAsync(result -> { // 异步？
//            println("第二步在第一步结果 +\",World\"");
//            return result + ",World";
//        }).thenAccept(CompletableFutureDemo::println) // 控制输出
//                .whenComplete((v, error) -> { // 返回值 void, 异常 -> 结束状态
//                    println("执行结束!");
//                })
////                .join() // 等待执行结束
//        ;
//        // 三段式编程
//        // 业务执行
//        // 执行完成
//        // 异常处理
//        // 命令编程方式 Imperative programming
//        try
//
//        {
//            // action
//        } catch (
//                Exception e)
//
//        {
//            // error
//        } finally
//
//        {
//            //  complete
//        }
//
//
//    }
//
//    private static void println(String message) {
//        System.out.printf("[线程 : %s] %s\n",
//                Thread.currentThread().getName(), // 当前线程名称
//                message);
//    }
}
