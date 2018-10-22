package com.gupao.micro.services.spring.cloud.client;

import com.gupao.micro.services.spring.cloud.client.annotation.EnableRestClient;
import com.gupao.micro.services.spring.cloud.client.event.HttpRemoteAppEventListener;
import com.gupao.micro.services.spring.cloud.client.service.feign.clients.SayingService;
import com.gupao.micro.services.spring.cloud.client.service.rest.clients.SayingRestService;
import com.gupao.micro.services.spring.cloud.client.stream.SimpleMessageService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication // 标准 Spring Boot 应用
@EnableDiscoveryClient // 激活服务发现客户端
@EnableScheduling
@EnableFeignClients(clients = SayingService.class) // 引入 FeignClient
@EnableRestClient(clients = SayingRestService.class) // 引入 @RestClient
@EnableBinding(SimpleMessageService.class) // 激活并引入 SimpleMessageService
public class SpringCloudClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .listeners(new HttpRemoteAppEventListener())
                .run(args);
    }
}
