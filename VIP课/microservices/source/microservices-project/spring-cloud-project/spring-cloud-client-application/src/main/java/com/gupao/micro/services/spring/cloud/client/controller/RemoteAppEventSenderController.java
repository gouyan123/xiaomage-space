package com.gupao.micro.services.spring.cloud.client.controller;

import com.gupao.micro.services.spring.cloud.client.event.RemoteAppEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 远程应用事件控制器
 */
@RestController
public class RemoteAppEventSenderController implements
        ApplicationEventPublisherAware {

    @Value("${spring.application.name}")
    public String currentAppName;

    private ApplicationEventPublisher publisher;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/send/remote/event")
    public String sendEvent(@RequestParam String message) {
        publisher.publishEvent(message);
        return "Sent";
    }

//    @GetMapping("/send/remote/event/{appName}")
//    public String sendAppCluster(@PathVariable String appName, @RequestParam String message) {
//        // 发送数据到自己
//        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(appName);
//        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(message, currentAppName, appName, serviceInstances);
//        // 发送事件当前上下文
//        publisher.publishEvent(remoteAppEvent);
//        return "Ok";
//    }

    @PostMapping("/send/remote/event/{appName}")
    public String sendAppCluster(@PathVariable String appName, @RequestBody Object data) {
        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(data, appName, true);
        // 发送事件当前上下文
        publisher.publishEvent(remoteAppEvent);
        return "Ok";
    }

//    @PostMapping("/send/remote/event/{appName}/{ip}/{port}")
//    public String sendAppInstance(@PathVariable String appName,
//                                  @PathVariable String ip,
//                                  @PathVariable int port,
//                                  @RequestBody Object data) {
//        ServiceInstance serviceInstance = new DefaultServiceInstance(appName, ip, port, false);
//        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(data, currentAppName, appName, Arrays.asList(serviceInstance));
//        // 发送事件当前上下文
//        publisher.publishEvent(remoteAppEvent);
//        return "Ok";
//    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

}
