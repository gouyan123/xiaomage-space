package com.gupao.springcloudbusdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gupao.springcloudbusdemo.bus.event.MessageRemoteApplicationEvent;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;

/**
 * Jackson 是 JSON 序列化/反序列化框架
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/24
 */
public class JacksonDemo {

    public static void main(String[] args) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        // 创建一个 MessageRemoteApplicationEvent 事件
        String message = "Hello,World";
        String originService = "user-service";
        String destinationService = "email-service";
        MessageRemoteApplicationEvent event
                = new MessageRemoteApplicationEvent(message, originService, destinationService);
        // 序列化成 JSON
        String json = objectMapper.writeValueAsString(event);
        System.out.println(json);
    }
}
