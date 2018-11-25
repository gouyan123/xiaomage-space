package com.gupao.spring;

import com.gupao.util.Utils;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * {@link ListenableFuture}
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-26
 **/
public class ListenableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        AsyncListenableTaskExecutor executor =
                new SimpleAsyncTaskExecutor("ListenableFutureDemo-");
        // ListenableFuture 实例
        ListenableFuture<Integer> future = executor.submitListenable(new Callable<Integer>() {
            public Integer call() throws Exception {
                return 1;
            }
        });

        // 添加 Callback
        future.addCallback(new ListenableFutureCallback<Integer>() {

            public void onFailure(Throwable ex) {
                Utils.println(ex);
            }

            public void onSuccess(@Nullable Integer result) {
                Utils.println(result);
            }
        });

        // 阻塞
        future.get();

        future = executor.submitListenable(new Callable<Integer>() {
            public Integer call() throws Exception {
                return 2;
            }
        });

        future.addCallback(new ListenableFutureCallback<Integer>() {

            public void onFailure(Throwable ex) {
                Utils.println(ex);
            }

            public void onSuccess(@Nullable Integer result) {
                Utils.println(result);
            }
        });

        // Future 直到计算结束（阻塞）
        future.get();

    }

}
