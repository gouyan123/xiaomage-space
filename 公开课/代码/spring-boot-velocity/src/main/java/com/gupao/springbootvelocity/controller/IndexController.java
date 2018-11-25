package com.gupao.springbootvelocity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Index
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/25
 */
@Controller
public class IndexController {

//    @RequestMapping("")
//    public ModelAndView index() { // 历史写法
//        ModelAndView modelAndView = new ModelAndView();
//        // 模(mu)板渲染
//        modelAndView.getModel().put("message", "Hello,World");
//        modelAndView.setViewName("index");
//        return modelAndView;
//    }

    @RequestMapping("")
    public String index(Model model) { // 现代写法
        // 模(mu)板渲染
        model.addAttribute("message", "Hello,World");
        return "index";
    }
}
