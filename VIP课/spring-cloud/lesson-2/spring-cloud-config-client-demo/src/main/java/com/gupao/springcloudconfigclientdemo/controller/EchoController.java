package com.gupao.springcloudconfigclientdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 输出配置项内容
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-25
 **/
@RestController
@RefreshScope
public class EchoController {

    @Value("${my.name}")
    private String myName;

    @GetMapping("/my-name")
    public String getName(){
        return myName;
    }

}
