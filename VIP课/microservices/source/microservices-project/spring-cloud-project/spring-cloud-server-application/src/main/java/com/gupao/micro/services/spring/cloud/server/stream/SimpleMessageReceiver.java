package com.gupao.micro.services.spring.cloud.server.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SimpleMessageReceiver {

    @Input("gupao2018")
    SubscribableChannel gupao();

    @Input("test007")
    SubscribableChannel testChannel();

    @Input("test-http")
    SubscribableChannel httpChannel();
}
