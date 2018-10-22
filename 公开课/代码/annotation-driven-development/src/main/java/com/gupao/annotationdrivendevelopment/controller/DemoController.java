package com.gupao.annotationdrivendevelopment.controller;

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

    @GetMapping("/helloWorld")
    public String helloWorld(){
        return "Hello, World!";
    }

}
