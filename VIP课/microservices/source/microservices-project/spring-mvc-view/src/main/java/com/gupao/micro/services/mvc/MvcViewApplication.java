package com.gupao.micro.services.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class MvcViewApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MvcViewApplication.class);
        springApplication.run(args);
    }
}
