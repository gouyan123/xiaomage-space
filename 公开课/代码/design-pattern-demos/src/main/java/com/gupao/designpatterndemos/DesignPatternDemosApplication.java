package com.gupao.designpatterndemos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Random;

@SpringBootApplication
@RestController
public class DesignPatternDemosApplication extends WebMvcConfigurerAdapter {


    private static final Random random = new Random();

    @GetMapping("")
    public String hello() {
        return "Hello,World";
    }

    public static void main(String[] args) {
        SpringApplication.run(DesignPatternDemosApplication.class, args);
    }
}
