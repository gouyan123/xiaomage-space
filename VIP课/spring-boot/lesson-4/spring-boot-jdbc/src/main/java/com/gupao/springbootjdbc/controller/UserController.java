package com.gupao.springbootjdbc.controller;

import com.gupao.springbootjdbc.domain.User;
import com.gupao.springbootjdbc.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 用户 RestController on WebMVC
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-15
 **/
@RestController
public class UserController {

    private final UserRepository userRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/web/mvc/user/save")
    public Boolean save(@RequestBody User user) throws ExecutionException, InterruptedException, SQLException {
//        Future<Boolean> future = executorService.submit(()->{
//            return userRepository.save(user);
//        });
//        return future.get();
        System.out.printf("[Thread : %s ] UserController starts saving user...\n",
                Thread.currentThread().getName());
        return userRepository.save(user);
    }

}
