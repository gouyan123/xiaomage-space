package com.gupao.reactor;

import com.gupao.util.Utils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * {@link Flux} 异步操作
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-26
 **/
public class FluxAsyncDemo {

    public static void main(String[] args) throws InterruptedException {
        // 当前线程执行
//        Flux.range(0,10)
//                .publishOn(Schedulers.immediate())
//                .subscribe(Utils::println);
        // 单线程异步执行
//        Flux.range(0, 10)
//                .publishOn(Schedulers.single())
//                .subscribe(Utils::println);

        // 弹性线程池异步执行
//        Flux.range(0, 10)
//                .publishOn(Schedulers.elastic())
//                .subscribe(Utils::println);

        // 并行线程池异步执行
        Flux.range(0, 10)
                .publishOn(Schedulers.parallel())
                .subscribe(Utils::println);

        // 强制让主线程执行完毕
        Thread.currentThread().join(1 * 1000L);
    }

}
