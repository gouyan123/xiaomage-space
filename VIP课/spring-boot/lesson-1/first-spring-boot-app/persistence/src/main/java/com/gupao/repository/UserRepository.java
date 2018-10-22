package com.gupao.repository;

import com.gupao.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 用户的仓储
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-18
 **/
@Repository
public class UserRepository {
    /**@Controller都是线程不安全的，处理要写成线程安全*/
    private final ConcurrentMap<Long, User> repository = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong();

    public Boolean save(User user) {
        // ID 从 1 开始
        long id = idGenerator.incrementAndGet();
        user.setId(id);
        // 1 -> user
        // 1 -> user1 -> user return
        return repository.put(id, user) == null;
    }

    // Effective Java II
    public Collection<User> findAll() {
        return repository.values();
    }
}
