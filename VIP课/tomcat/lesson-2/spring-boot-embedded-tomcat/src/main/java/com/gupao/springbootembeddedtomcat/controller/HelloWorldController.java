package com.gupao.springbootembeddedtomcat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/1/20
 */
@RestController
public class HelloWorldController {

    @GetMapping("")
    public String index(){
        return "Hello,World";
    }
}
