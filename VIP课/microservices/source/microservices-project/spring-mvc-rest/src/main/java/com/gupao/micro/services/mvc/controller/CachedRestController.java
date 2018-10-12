package com.gupao.micro.services.mvc.controller;

import com.gupao.micro.services.mvc.annotation.OptionsMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class CachedRestController {

    @RequestMapping
    @ResponseBody // 没有缓存 -> 304
    // 服务端和客户端没有形成默契（状态码）
    // HTTP 协议，REST 继承
    public String helloWorld() { // 200 / 500  / 400
        return "Hello,World"; // Body = "Hello,World" String
    }

    @RequestMapping("/cache") //  Spring MVC 返回值处理
    @OptionsMapping(name="")
    public ResponseEntity<String> cachedHelloWorld(
            @RequestParam(required = false, defaultValue = "false") boolean cached) {
        if (cached) {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        } else {
            return ResponseEntity.ok("Hello,World");
        }
    }

}
