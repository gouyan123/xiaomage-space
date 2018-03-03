package com.gupao.design;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * {@link ExecutorService} 示例
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/3
 */
public class ExecutorServiceDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Runnable 接口、Callable 接口
        // 假设 Runnable 在 JDK 1.1 时，提供 throws Exception
        // 程序在 JDK 1.0 运行时，出现不兼容
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello,World");
            }
        });

        executorService.execute(() -> System.out.println("Hello,World"));

        executorService.execute(() -> {
            throw new RuntimeException("Hello,World");
        });
        // Throwable : Exception \ Error 父类
        Future<String> future = executorService.submit(() -> "Hello,World");
        // Checked 异常：需要明确在方法签名出现
        // Unchecked 异常：不强制在方法签名出现，建议还是出现，NullPointerException
        System.out.println(future.get());

        executorService.shutdown();

        System.out.println("Complete...");
    }
}
