package com.gupao.micro.services.mvc.controller;

import com.alibaba.fastjson.JSON;
import com.gupao.micro.services.mvc.annotation.OptionsMapping;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@SpringBootApplication
@Controller
public class CachedRestController{
    /**状态码304表示不会被改变；这种没有缓存，因为服务端和客户端没有形成默契；这是HTTP协议，被REST继承了*/
    @RequestMapping
    @ResponseBody
    public String helloWorld() {
        // Body = "Hello,World"
        return "Hello,World";
    }

    /**ResponseEntity = 响应头 + 响应体*/
    @RequestMapping("/cache") //  Spring MVC 返回值处理
    @OptionsMapping(name="")
    public ResponseEntity<String> cachedHelloWorld(@RequestParam(required = false, defaultValue = "false") boolean cached) {
        /**如果用缓存，直接返回响应头；如果不用缓存，要返回 响应头 + 响应体*/
        if (cached) {
            /**     responseEntity  =  responseBody   +   responseHeader*/
            return new ResponseEntity("hello world", HttpStatus.NOT_MODIFIED);
        } else {
            return ResponseEntity.ok("Hello,World");
        }
    }
}
