package com.gupao.micro.services.spring.cloud.server.web.mvc;

import com.gupao.micro.services.spring.cloud.server.controller.ServerController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice(assignableTypes = ServerController.class)
public class CircuitBreakerControllerAdvice {

    @ExceptionHandler
    public void onTimeoutException(TimeoutException timeoutException,
                                   Writer writer) throws IOException {
        writer.write(errorContent("")); // 网络 I/O 被容器
        writer.flush();
        writer.close();
    }

    public String errorContent(String message) {
        return "Fault";
    }

}
