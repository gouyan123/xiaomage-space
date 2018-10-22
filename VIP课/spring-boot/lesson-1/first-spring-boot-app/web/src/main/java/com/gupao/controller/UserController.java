package com.gupao.controller;

import com.gupao.domain.User;
import com.gupao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户 控制器
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-18
 **/
@RestController
public class UserController {
    // 保存使用 Spring Web MVC

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user/save")
    public User user(@RequestParam String name) {
        User user = new User();
        user.setName(name);
        userRepository.save(user);
        return user;
    }
}
