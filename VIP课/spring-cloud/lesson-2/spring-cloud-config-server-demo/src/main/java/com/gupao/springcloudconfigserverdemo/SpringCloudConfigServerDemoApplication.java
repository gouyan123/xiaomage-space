package com.gupao.springcloudconfigserverdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Set;

@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServerDemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigServerDemoApplication.class, args);
    }


}
