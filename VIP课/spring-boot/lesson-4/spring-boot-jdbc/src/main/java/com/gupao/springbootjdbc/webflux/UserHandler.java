package com.gupao.springbootjdbc.webflux;

import com.gupao.springbootjdbc.domain.User;
import com.gupao.springbootjdbc.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * TODO:小马哥，写点注释吧！
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-15
 **/
@Component
public class UserHandler {

    private final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        // 在 Spring Web MVC 中使用 @RequestBody
        // 在 Spring Web Flux 使用 ServerRequest
        // Mono<User> 类似于 Optional<User>
        System.out.printf("[Thread : %s ] UserHandler starts saving user...\n",
                Thread.currentThread().getName());
        Mono<User> userMono = serverRequest.bodyToMono(User.class);
        // map 相当于 转化工作
        Mono<Boolean> booleanMono = userMono.map(userRepository::save);
        return ServerResponse.ok().body(booleanMono,Boolean.class);
    }


}
