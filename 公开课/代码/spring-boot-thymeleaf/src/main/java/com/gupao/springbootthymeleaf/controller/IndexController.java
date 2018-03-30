package com.gupao.springbootthymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/30
 */
@Controller
public class IndexController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("message","Hello,World");
        return "index";
    }
}
