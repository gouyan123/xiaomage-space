package study.java8.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<?> future  = executorService.submit(()->{

            System.out.println("Hello,World");

        });

        while(!future.isDone()){

        }

        executorService.shutdown();
    }

}
