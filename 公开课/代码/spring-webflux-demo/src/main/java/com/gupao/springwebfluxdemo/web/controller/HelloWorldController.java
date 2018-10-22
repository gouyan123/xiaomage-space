package com.gupao.springwebfluxdemo.web.controller;

import com.gupao.springwebfluxdemo.domain.User;
import com.gupao.springwebfluxdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Optional;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/2
 */
@Controller
public class HelloWorldController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @ResponseBody
    public Optional<String> index(@RequestParam(required = false)
                                          String message) {
        return StringUtils.hasText(message) ? Optional.of(message) : Optional.empty();
    }

    @PostMapping("/user/save")
    @ResponseBody
    public Mono<User> saveUser(User user) {
        if (userRepository.saveUser(user)) {
            return Mono.just(user);
        }
        return Mono.empty();
    }

    @GetMapping("/user/list")
    @ResponseBody
    public Collection<User> users() {
        return userRepository.findAll();
    }

    @GetMapping("/user/flux")
    @ResponseBody
    public Flux<User> usersList() {
        Collection<User> users = userRepository.findAll();
        return Flux.fromIterable(users);
    }


}
