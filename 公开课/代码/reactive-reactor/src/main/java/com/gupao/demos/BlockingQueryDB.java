package com.gupao.demos;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class BlockingQueryDB {

    public Integer[] queryA() {
        sleep(200);
        System.out.printf("Thread[%s] queryA()\n", Thread.currentThread().getName());
        return of(1, 2, 3, 4, 5);
    }

    public Integer[] queryB() {
        sleep(300);
        System.out.printf("Thread[%s] queryB()\n", Thread.currentThread().getName());
        return of(6, 7, 8, 9, 10);
    }

    private static <T> T[] of(T... values) {
        return values;
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BlockingQueryDB blockingQueryDB = new BlockingQueryDB();
        // 阻塞模式
        blockingMode(blockingQueryDB);
        // 并发模式
        concurrentMode(blockingQueryDB);
        // 响应模式
        reactiveMode(blockingQueryDB);

        sleep(10000);
    }

    /**
     * 响应模式
     *
     * @param blockingQueryDB
     */
    private static void reactiveMode(BlockingQueryDB blockingQueryDB) {

        long startTime = System.currentTimeMillis();

        // Integer[] a = blockingQueryDB.queryA();

        // Integer[] b = blockingQueryDB.queryB();

        // Publisher

        // 流式处理机制，类似 Stream -> Flowable
        // 开发人员并需关注并发或者线程池

        // 发布 queryA() 数据
        Flowable<Integer[]> observableA = Flowable.fromCallable(blockingQueryDB::queryA);


        // p(5)  +1  = 6
        // c

        Lock lock = new ReentrantLock();

        lock.lock();;  // AQS state + 1
        lock.unlock(); // AQS state -1

        observableA
                .subscribeOn(Schedulers.newThread())
                .subscribe(); // 没有订阅就没有执行

        // 发布 queryB() 数据
        Flowable<Integer[]> observableB = Flowable.fromCallable(blockingQueryDB::queryB);



        observableB
                .subscribeOn(Schedulers.newThread()) // 决定同步/异步
                .doOnNext(value -> {
                    // 数据消费正常逻辑
                })
                .doOnError(e -> {
                    // 数据消费异常逻辑
                })
                .doOnComplete(() -> {
                    // 执行结束逻辑
                });

        // [1...5] [6...10] 合并 => [1...10]
        observableA.mergeWith(observableB);

        // just 方法是完全调用 queryA() + queryB() >= 阻塞模式

        long costTime = System.currentTimeMillis() - startTime;

        System.out.println("reactiveMode -  queryA() + queryB() cost time : " + costTime);

    }

    /**
     * 并发模式
     *
     * @param blockingQueryDB
     */
    private static void concurrentMode(BlockingQueryDB blockingQueryDB) {

        long startTime = System.currentTimeMillis();

        // 线程池
        ExecutorService executorService = newFixedThreadPool(2);
        // Future A 保存 queryA() 结果
        Future<Integer[]> futureA = executorService.submit(blockingQueryDB::queryA);
        // Future B 保存 queryB() 结果
        Future<Integer[]> futureB = executorService.submit(blockingQueryDB::queryB);

        try {
            // 阻塞
            futureB.get();  // 正常逻辑

            // 阻塞
            futureA.get();
            // 大于 150 ms
            // 还有优化写法
        } catch (Exception e) { // 异常逻辑
        }

        // 结束后的逻辑

        long costTime = System.currentTimeMillis() - startTime;

        executorService.shutdown();

        System.out.println("concurrentMode -  queryA() + queryB() cost time : " + costTime);
    }

    /**
     * 阻塞模式
     *
     * @param blockingQueryDB
     */
    private static void blockingMode(BlockingQueryDB blockingQueryDB) {
        long startTime = System.currentTimeMillis();

        //  Query A   ( 100ms )
        Integer[] a = blockingQueryDB.queryA();
        //  Query B ( 150ms )
        Integer[] b = blockingQueryDB.queryB();

        long costTime = System.currentTimeMillis() - startTime;

        System.out.println("blockingMode -  queryA() + queryB() cost time : " + costTime);

    }

}
