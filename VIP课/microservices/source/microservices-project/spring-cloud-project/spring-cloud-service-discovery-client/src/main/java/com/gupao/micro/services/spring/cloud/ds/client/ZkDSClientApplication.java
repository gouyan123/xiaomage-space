package com.gupao.micro.services.spring.cloud.ds.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 尽可能使用 @EnableDiscoveryClient
public class ZkDSClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkDSClientApplication.class, args);
    }
}
