package com.gupao.service.impl;

import com.gupao.domain.User;
import com.gupao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link  UserService} 实现
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/1/28
 */
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Override
    public boolean addUser(User user) {
        logger.info("Add " + user);
        return true;
    }
}
