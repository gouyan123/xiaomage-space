package com.gupao.spring.webmvc.auto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO:小马哥，写点注释吧！
 * 广告资源位...
 *
 * @author mercyblitz
 * @date 2017-10-09
 **/
@RestController
public class DemoController {

    // 有可能 helloWorld Bean获取不到
    @Autowired(required = false)
    @Qualifier("helloWorld")
    private String helloWorld;

    @GetMapping
    public String index(){
        return helloWorld;
    }
}

