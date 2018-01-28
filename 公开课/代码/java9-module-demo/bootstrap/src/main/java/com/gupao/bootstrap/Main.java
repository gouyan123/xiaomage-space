package com.gupao.bootstrap;

import com.gupao.domain.User;
import com.gupao.service.UserService;
import com.gupao.service.impl.UserServiceImpl;

/**
 * 引导类
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/1/28
 */
public class Main {

    public static void main(String[] args) {

        User user = new User();
        user.setId(1L);
        user.setName("小马哥");
        UserService userService = new UserServiceImpl();
        userService.addUser(user);


    }

}
