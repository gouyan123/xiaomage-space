package com.gupao.reactor;

import com.gupao.util.Utils;
import reactor.core.publisher.Flux;

import java.util.Random;

/**
 * {@link Flux} API
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-26
 **/
public class FluxAPIDemo {

    public static void main(String[] args) {
        Random random = new Random();
        // 订阅并且处理（控制台输出）
        Flux.generate(() -> 0, (value, sink) -> {

            if (value == 3) {
                sink.complete(); // 主动完成
            } else {
                sink.next("value = " + value);
            }

            return value + 1;
        }).subscribe(Utils::println,Utils::println,()->{
            Utils.println("Subscription is completed!");
        });


    }

}
