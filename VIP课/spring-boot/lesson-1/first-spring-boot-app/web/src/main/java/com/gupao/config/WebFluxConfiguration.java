package com.gupao.config;

import com.gupao.domain.User;
import com.gupao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import java.util.Collection;


/**
 *  Web Flux 参数
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-18
 **/
@Configuration
public class WebFluxConfiguration {

    @Bean
    @Autowired
    public RouterFunction<ServerResponse> routerFunctionAllUsers(UserRepository userRepository){
        /**Collection extends Iterable是一个迭代器*/
        Collection<User> users = userRepository.findAll();
        Flux<User> userFlux = Flux.fromIterable(users);
//        Mono<Collection<User>> mono = Mono.just(users);
        /**route：路由到 "/all-users"路径下；ok()：返回状态码200；body()：传publisher和clazz
         * Mono：0-1个元素；Flux：0-n个元素；*/
        return RouterFunctions.route(RequestPredicates.path("/all-users"),
                request -> ServerResponse.ok().body(userFlux,User.class));
    }

    @Bean
    @Autowired
    public RouterFunction<ServerResponse> routerFunctionUsers(UserRepository userRepository){
        Collection<User> users = userRepository.findAll();
        Flux<User> userFlux = Flux.fromIterable(users);
//        Mono<Collection<User>> mono = Mono.just(users);
        return RouterFunctions.route(RequestPredicates.path("/users"),
                request -> ServerResponse.ok().body(userFlux,User.class));
    }

}
