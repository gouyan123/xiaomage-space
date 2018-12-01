package com.gupao.micro.services.spring.cloud.client.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**实现ApplicationEventPublisherAware接口，覆写setApplicationEventPublisher()方法，获取 publisher对象*/
@RestController
public class SpringEventController implements ApplicationEventPublisherAware {
    /*publisher事件发送者，publishEvent(msg)发送事件并携带msg*/
    private ApplicationEventPublisher publisher;

    @GetMapping("/send/event")
    public String sendEvent(@RequestParam String message) {
        publisher.publishEvent(message);
        return "Sent";
    }

    @EventListener
    public void onMessage(PayloadApplicationEvent event) {
        System.out.println("接受事件：" + event.getPayload());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
