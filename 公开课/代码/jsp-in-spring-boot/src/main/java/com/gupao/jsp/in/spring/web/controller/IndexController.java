package com.gupao.jsp.in.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Index Controller(Application Controller)
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/1/10
 */
@Controller
public class IndexController {


    @RequestMapping("")
    public String index(@RequestParam(required = false) String message,
                        Model model) {

        model.addAttribute("message", message);

        return "index";
    }

}
