package com.gupao.spring5testdemo.controller;

import com.gupao.spring5testdemo.domain.User;
import com.gupao.spring5testdemo.service.UserRemoteService;
import com.gupao.spring5testdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  User Controller
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/9
 */
@RestController
public class UserController {

    @Autowired
    private UserRemoteService userRemoteService;

    public List<User> findAll() {
        return userRemoteService.findAll();
    }

}
