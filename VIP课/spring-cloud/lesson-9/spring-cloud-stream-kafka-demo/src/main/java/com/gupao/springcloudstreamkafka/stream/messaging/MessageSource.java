package com.gupao.springcloudstreamkafka.stream.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 自定义消息源
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/15
 */
public interface MessageSource {

    /**
     * 消息来源的管道名称："gupao"
     */
    String OUTPUT = "gupao";

    @Output(OUTPUT)
    MessageChannel gupao();

}
