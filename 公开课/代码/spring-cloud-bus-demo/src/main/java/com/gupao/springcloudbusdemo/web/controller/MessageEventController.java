package com.gupao.springcloudbusdemo.web.controller;

import com.gupao.springcloudbusdemo.bus.event.MessageRemoteApplicationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * 消息事件 {@link Controller}
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/24
 */
@RestController
public class MessageEventController
        implements ApplicationListener<MessageRemoteApplicationEvent> {

    @Value("${spring.application.name}")
    private String originService;
    // REST HTTP 客户端
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 发送异步 {@link MessageRemoteApplicationEvent}
     *
     * @param message
     * @param destinationService
     * @return
     */
    @GetMapping("send/async/event")
    public MessageRemoteApplicationEvent sendAsyncEvent(@RequestParam String message,
                                                        @RequestParam String destinationService) {

        MessageRemoteApplicationEvent event
                = new MessageRemoteApplicationEvent(message, applicationContext.getId(), destinationService);

        applicationContext.publishEvent(event);

        return event;
    }

    @Override
    public void onApplicationEvent(MessageRemoteApplicationEvent event) {

        System.err.println(event.getOriginService());

    }

    @GetMapping("send/sync/event")
    public MessageRemoteApplicationEvent sendSyncEvent(@RequestParam String message,
                                                       @RequestParam String destinationService) {

        MessageRemoteApplicationEvent event
                = new MessageRemoteApplicationEvent(message, originService, destinationService);
        // 目标地址
        // originService  : 8080
        // destinationService = localhost:8081
        // url = "http://localhost:8081/receive/sync/event"
        String url = "http://" + destinationService + "/receive/sync/event";
        return restTemplate.postForObject(url, event, MessageRemoteApplicationEvent.class);
    }

    /**
     * 接收 {@link MessageRemoteApplicationEvent} 并且返回
     *
     * @param event
     * @return
     */
    @PostMapping("receive/sync/event")
    public MessageRemoteApplicationEvent receiveSyncEvent(@RequestBody MessageRemoteApplicationEvent
                                                                  event) {
        return event;
    }


}
