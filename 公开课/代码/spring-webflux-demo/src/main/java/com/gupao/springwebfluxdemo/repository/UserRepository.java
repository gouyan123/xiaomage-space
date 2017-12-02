package com.gupao.springwebfluxdemo.repository;

import com.gupao.springwebfluxdemo.domain.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/2
 */
@Repository
public class UserRepository {

    private Map<Long, User> repository = new ConcurrentHashMap<Long, User>();

    public boolean saveUser(User user) {
        return repository.put(user.getId(), user) == null;
    }

    public Collection<User> findAll() {
        return repository.values();
    }

    public Flux<User> allUsers(){
        return Flux.fromIterable(findAll());
    }

}
