package com.gupao.micro.services.spring.cloud.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ServerController {

    @Value("${spring.application.name}")
    private String currentServiceName;

    @GetMapping("/say")
    public String say(@RequestParam String message) {
        System.out.println("ServerController 接收到消息 - say : " + message);
        return "Hello, " + message;
    }

}

