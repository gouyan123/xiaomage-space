package com.gupao.micro.services.spring.cloud.server;

import com.gupao.micro.services.spring.cloud.server.aop.ServerControllerAspect;
import com.gupao.micro.services.spring.cloud.server.stream.SimpleMessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Indexed;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

@SpringBootApplication // 标准 Spring Boot 应用
@EnableDiscoveryClient // 激活服务发现客户端
@EnableHystrix               // 激活 Hystrix
@EnableAspectJAutoProxy(proxyTargetClass = true) // 激活 AOP
@EnableBinding(SimpleMessageReceiver.class) // 激活并引入 SimpleMessageReceiver
@EnableAsync
@Indexed
public class SpringCloudServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @Autowired
    private SimpleMessageReceiver simpleMessageReceiver;


    @PostConstruct
    public void init() {  // 接口编程
        // 获取 SubscribableChannel
        SubscribableChannel subscribableChannel = simpleMessageReceiver.gupao();
        subscribableChannel.subscribe(message -> {
            MessageHeaders headers = message.getHeaders();
            String encoding = (String) headers.get("charset-encoding");
            String text = (String) headers.get("content-type");
            byte[] content = (byte[]) message.getPayload();
            try {
                System.out.println("接受到消息：" + new String(content, encoding));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }

    @StreamListener("gupao2018")  // Spring Cloud Stream 注解驱动
    public void onMessage(byte[] data) {
        System.out.println("onMessage(byte[]): " + new String(data));
    }

    @StreamListener("gupao2018")  // Spring Cloud Stream 注解驱动
    public void onMessage(String data) {
        System.out.println("onMessage(String) : " + data);
    }

    @StreamListener("gupao2018") // Spring Cloud Stream 注解驱动
    public void onMessage2(String data2) {
        System.out.println("onMessage2(String) : " + data2);
    }

    @ServiceActivator(inputChannel = "gupao2018") // Spring Integration 注解驱动
    public void onServiceActivator(String data) {
        System.out.println("onServiceActivator(String) : " + data);
    }

    @StreamListener("test007")  // Spring Cloud Stream 注解驱动
    public void onMessageFromRocketMQ(byte[] data) {
        System.out.println("RocketMQ - onMessage(byte[]): " + new String(data));
    }

    @StreamListener("test-http")  // Spring Cloud Stream 注解驱动
    public void onMessageFromHttp(byte[] data) {
        System.out.println("HTTP - onMessage(byte[]): " + new String(data));
    }
}
