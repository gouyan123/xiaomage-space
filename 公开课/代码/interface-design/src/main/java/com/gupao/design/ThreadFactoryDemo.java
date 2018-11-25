package com.gupao.design;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * {@link ThreadFactory} 实例
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/3
 */
public class ThreadFactoryDemo {

    // 1. 接口或者抽象类
    // 2. 创建实例
    // 3. 抽象方法

    // ThreadFactory
    public static void main(String[] args) {

        ThreadFactory factory = new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable);
            }
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor(factory);

        executorService.execute(()-> System.out.println("Hello,World"));

        executorService.shutdown();

    }
}
