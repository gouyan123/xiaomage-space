package com.gupao.micro.services.reactive.webflux;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class WebFluxController {

    @RequestMapping("")
    public Mono<String> index() {
        println("执行计算");
        Mono<String> result =  Mono.fromSupplier(() -> {
            println("返回结果");
            return "Hello,World";
        });
        return result;
    }

    private static void println(String message) {
        System.out.printf("[线程 : %s] %s\n",
                Thread.currentThread().getName(), // 当前线程名称
                message);
    }
}
