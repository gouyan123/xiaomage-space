package com.gupao.micro.services.spring.cloud.client.controller;

import com.gupao.micro.services.spring.cloud.client.stream.SimpleMessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SimpleMessageService simpleMessageService;

    @GetMapping
    public String send(@RequestParam String message) {
        rabbitTemplate.convertAndSend("Hello,World");
        return "OK";
    }

    @GetMapping("/stream/send")
    public boolean streamSend(@RequestParam String message) {
        // 获取 MessageChannel
        MessageChannel messageChannel = simpleMessageService.gupao();
        Map<String, Object> headers = new HashMap<>();
        headers.put("charset-encoding", "UTF-8");
        headers.put("content-type", MediaType.TEXT_PLAIN_VALUE);
        return messageChannel.send(new GenericMessage(message, headers));
    }

    @GetMapping("/stream/send/rocketmq")
    public boolean streamSendToRocketMQ(@RequestParam String message) {
        // 获取 MessageChannel
        MessageChannel messageChannel = simpleMessageService.testChannel();
        return messageChannel.send(new GenericMessage(message));
    }

    @GetMapping("/stream/send/http")
    public boolean streamSendToHttp(@RequestParam String message) {
        // 获取 MessageChannel
        MessageChannel messageChannel = simpleMessageService.http();
        return messageChannel.send(new GenericMessage(message));
    }
}
