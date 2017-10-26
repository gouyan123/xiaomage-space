package com.gupao.reactor;

import com.gupao.util.Utils;
import reactor.core.publisher.Flux;

import java.util.Random;

/**
 * {@link Flux}
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-26
 **/
public class FluxLambdaDemo {

    public static void main(String[] args) {
        Random random = new Random();
        // 订阅并且处理（控制台输出）
        Flux.just(1, 2, 3).map(value -> {
            // 当 随机数 == 3 抛出异常
            if (random.nextInt(4) == 3) {
                throw new RuntimeException("value must be less than 3!");
            }
            return value + 1;
        }).subscribe(
                Utils::println, // 处理数据 onNext()
                Utils::println, // 处理异常 onError()
                () -> {
                    Utils.println("Subscription is completed!");
                }
        );


    }

}
