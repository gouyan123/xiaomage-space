package com.gupao.design;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 回调接口
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/3
 */
public class CallbackDemo {

    public static void main(String[] args) {
        // Callback 接口是不程序显示地执行

        CallbackExecutor executor = new CallbackExecutor();
        // 执行回调
        executor.execute(() -> System.out.println("Hello,World"));
        executor.execute(() -> System.out.println("ABC"));

        executor.run();
    }

    public interface Callback {

        void callback();

    }

    public static class CallbackExecutor {

        private Queue<Callback> callbackQueue = new LinkedList();

        public void execute(Callback callback) {
            callbackQueue.add(callback);
        }

        public void run() {
            callbackQueue.forEach(callback -> callback.callback());
        }
    }

    private void async() { // 异步回调

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            System.out.println("Hello,World");
        });

        System.out.println("ABC");

        executorService.shutdown();
    }
}
