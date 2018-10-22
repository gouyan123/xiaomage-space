package concurrent.java9;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * {@link SubmissionPublisher}
 *
 * @author mercyblitz
 * @date 2017-09-29
 **/
public class SubmissionPublisherDemo {

    public static void main(String[] args) throws InterruptedException {

        try (SubmissionPublisher<Integer> publisher =
                     new SubmissionPublisher<>()) {

            publisher.subscribe(new IntegerSubscriber("A"));
            publisher.subscribe(new IntegerSubscriber("B"));
            publisher.subscribe(new IntegerSubscriber("C"));

            CompletableFuture<Void> completableFuture = publisher.consume(value -> {
                System.out.printf(
                        "Current Thread[%s]  consumes value[%s]\n",
                        Thread.currentThread().getName(),
                        value);
            }).thenRunAsync(()->{
                System.out.printf(
                "Current Thread[%s] thenRunAsync \n",
                        Thread.currentThread().getName());

            });



            // 提交数据到各个订阅器
            publisher.submit(100);

        }
        ;

//        try{
//
//        }finally {
//            publisher.close();
//        }


        Thread.currentThread().join(1000L);

    }

    private static class IntegerSubscriber implements
            Flow.Subscriber<Integer> {

        private final String name;

        private Flow.Subscription subscription;

        private IntegerSubscriber(String name) {
            this.name = name;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            System.out.printf(
                    "Thread[%s] Current Subscriber[%s] " +
                            "subscribes subscription[%s]\n",
                    Thread.currentThread().getName(),
                    name,
                    subscription);
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(Integer item) {
            System.out.printf(
                    "Thread[%s] Current Subscriber[%s] " +
                            "receives item[%d]\n",
                    Thread.currentThread().getName(),
                    name,
                    item);
            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onComplete() {
            System.out.printf(
                    "Thread[%s] Current Subscriber[%s] " +
                            "is completed!\n",
                    Thread.currentThread().getName(),
                    name);
        }

    }

}
