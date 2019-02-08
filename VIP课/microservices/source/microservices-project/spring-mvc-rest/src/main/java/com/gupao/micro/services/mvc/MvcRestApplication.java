package com.gupao.micro.services.mvc;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.gupao.micro.services.mvc.controller")
public class MvcRestApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MvcRestApplication.class)
                .run(args);
    }

}
