package com.gupao.micro.services.mvc.service;

import com.gupao.micro.services.mvc.annotation.TransactionalService;

@TransactionalService(value = "echoService-2018", txName = "myTxName") // @Service Bean + @Transactional
// 定义它的 Bean 名称
public class EchoService {
    public void echo(String message) {
        System.out.println(message);
    }
}
