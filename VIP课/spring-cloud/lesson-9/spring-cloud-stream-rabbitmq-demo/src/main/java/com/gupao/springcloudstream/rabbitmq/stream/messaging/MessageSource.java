package com.gupao.springcloudstream.rabbitmq.stream.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/15
 */
public interface MessageSource {

    /**
     * 消息来源的管道名称："gupao"
     */
    String NAME = "gupao";

    @Output(NAME)
    MessageChannel gupao();

}
