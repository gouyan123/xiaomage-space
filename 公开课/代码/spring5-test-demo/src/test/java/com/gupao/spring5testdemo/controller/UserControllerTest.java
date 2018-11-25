package com.gupao.spring5testdemo.controller;


import com.gupao.spring5testdemo.domain.User;
import com.gupao.spring5testdemo.service.UserRemoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * {@link UserController} 测试
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/9
 */
@SpringJUnitConfig(classes = {UserController.class, UserControllerTest.TestConfiguration.class})
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    public void testFindAll() {
        // 这个返回结果 UserController#findAll() -> UserRemoteService#findAll();
        // 实际调用时 Mock UserRemoteService Bean
        List<User> users = userController.findAll();
        assertEquals(1, users.size());
        assertEquals("小马哥", users.get(0).getName());
    }


    /**
     * Test 配置
     */
    @Configuration
    public static class TestConfiguration {

        // 通过 Mockito Mock UserRemoteService 作为 Spring Bean
        @Bean
        public UserRemoteService userRemoteService() {
            UserRemoteService userRemoteService = mock(UserRemoteService.class);
            User user = new User();
            user.setId(1L);
            user.setName("小马哥");
            when(userRemoteService.findAll()).thenReturn(Arrays.asList(user));
            return userRemoteService;
        }
    }
}
