package com.gupao.springcloudstreamkafka.stream.producer;

import com.gupao.springcloudstreamkafka.stream.messaging.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 消息生成者 Bean
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/15
 */
@Component
@EnableBinding({Source.class,MessageSource.class})
public class MessageProducerBean {

    @Autowired
    @Qualifier(Source.OUTPUT) // Bean 名称
    private MessageChannel messageChannel;

    @Autowired
    private Source source;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    @Qualifier(MessageSource.OUTPUT) // Bean 名称
    private MessageChannel gupaoMessageChannel;

    /**
     * 发送消息
     * @param message 消息内容
     */
    public void send(String message){
        // 通过消息管道发送消息
//        messageChannel.send(MessageBuilder.withPayload(message).build());
        source.output().send(MessageBuilder.withPayload(message).build());
    }

    /**
     * 发送消息到 Gupao
     * @param message 消息内容
     */
    public void sendToGupao(String message){
        // 通过消息管道发送消息
        gupaoMessageChannel.send(MessageBuilder.withPayload(message).build());
    }

}
