package com.gupao.micro.services.spring.cloud.client.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SimpleMessageService {

    @Output("gupao2018") // Channel name
    MessageChannel gupao(); //  destination = test2018

    @Output("test007")
    MessageChannel testChannel(); //  destination = test007

    @Output("test-http")
    MessageChannel http(); //  destination = http
}
