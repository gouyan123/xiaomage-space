package com.gupao.flow;

import com.gupao.util.Utils;

import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * Java 9 {@link Flow} Demo
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-26
 **/
public class FlowDemo {

    public static void main(String[] args) throws InterruptedException {

        // 发布者
        try (SubmissionPublisher<String> publisher = new SubmissionPublisher<>()) {
            // 订阅
            publisher.subscribe(new StringSubscriber("A"));
            publisher.subscribe(new StringSubscriber("B"));
            publisher.subscribe(new StringSubscriber("C"));

            // 发布数据 "Hello,World
            publisher.submit("Hello,World");

        }

        // 主线程等待
        Thread.currentThread().join(2 * 1000L);
    }

    /**
     * 订阅者
     */
    private static class StringSubscriber implements Flow.Subscriber<String> {

        private static final Random random = new Random();

        private final String name;

        private Flow.Subscription subscription;

        private StringSubscriber(String name) {
            this.name = name;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            Utils.println("订阅者[" + name + "] 开始订阅！");
            // 向服务器反向请求
            subscription.request(1);
            this.subscription = subscription;
        }

        @Override
        public void onNext(String item) {
            Utils.println("订阅者[" + name + "] 接受数据：" + item);
            if (random.nextBoolean()) {
                subscription.cancel();
            } else {
                throw new RuntimeException();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Utils.println("订阅者[" + name + "] 订阅异常：" + throwable.getMessage());
        }

        @Override
        public void onComplete() {
            Utils.println("订阅者[" + name + "] 完成订阅！");
        }
    }

}
