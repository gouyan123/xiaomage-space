package com.gupao.user.web.controller;

import com.gupao.user.domain.User;
import com.gupao.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * 用户服务 REST API
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/10/28
 */
@RestController
public class UserRestApiController {

    @Autowired
    private UserService userService;

    /**
     * @param name 请求参数名为“name”的数据
     * @return 如果保存成功的话，返回{@link User},否则返回<code>null</code>
     */
    @PostMapping("/user/save")
    public User saveUser(@RequestParam String name) {
        User user = new User();
        user.setName(name);
        if (userService.save(user)) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * 罗列所有的用户数据
     * @return 所有的用户数据
     */
    @GetMapping("/user/list")
    public Collection<User> list() {
        return userService.findAll();
    }

}
