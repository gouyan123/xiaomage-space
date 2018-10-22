package com.gupao.springcloudstreamkafka.web.controller;

import com.gupao.springcloudstreamkafka.stream.producer.MessageProducerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kafka 生产者 Controller
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/12
 */
@RestController
public class KafkaProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final MessageProducerBean messageProducerBean;

    private final String topic;

    @Autowired
    public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplate,
                                   MessageProducerBean messageProducerBean,
                                   @Value("${kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageProducerBean = messageProducerBean;
        this.topic = topic;
    }

    /**
     * 通过 {@link KafkaTemplate}  发送
     * @param message
     * @return
     */
    @PostMapping("/message/send")
    public Boolean sendMessage(@RequestParam String message) {
        kafkaTemplate.send(topic, message);
        return true;
    }

    /**
     * 通过{@link MessageProducerBean} 发送
     * @param message
     * @return
     */
    @GetMapping("/message/send")
    public Boolean send(@RequestParam String message) {
        messageProducerBean.send(message);
        return true;
    }

    @GetMapping("/message/send/to/gupao")
    public Boolean sendToGupao(@RequestParam String message) {
        messageProducerBean.sendToGupao(message);
        return true;
    }

}
