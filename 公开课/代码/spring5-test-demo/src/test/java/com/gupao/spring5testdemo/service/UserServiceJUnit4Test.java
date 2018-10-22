package com.gupao.spring5testdemo.service;

import com.gupao.spring5testdemo.domain.User;
import com.gupao.spring5testdemo.service.impl.InMemoryUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * {@link UserService} JUnit 4 测试
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/9
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = InMemoryUserService.class)
public class UserServiceJUnit4Test {

    @Autowired
    private UserService userService;

    /**
     * 测试 {@link UserService#save(User)} 方法
     */
    @Test
    public void testSave(){
        User user = new User();
        user.setId(1L);
        user.setName("小马哥");
        // 第一次存，返回true
        assertTrue(userService.save(user));
        // 第二次存相同的内容，返回false
        assertFalse(userService.save(user));
    }

}
