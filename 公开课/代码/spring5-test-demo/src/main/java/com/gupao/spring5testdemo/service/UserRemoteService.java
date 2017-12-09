package com.gupao.spring5testdemo.service;

import com.gupao.spring5testdemo.domain.User;

import java.util.List;

/**
 * 用户的远程服务
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/9
 */
public interface UserRemoteService {

    List<User> findAll();
}
