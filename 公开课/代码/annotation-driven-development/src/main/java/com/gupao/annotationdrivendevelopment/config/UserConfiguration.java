package com.gupao.annotationdrivendevelopment.config;

import com.gupao.annotationdrivendevelopment.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User  配置 Bean
 * 提到 XML 配置文件
 *
 * @author mercyblitz
 * @date 2017-10-09
 **/
@Configuration
public class UserConfiguration {

    @Bean(name = "user")
    public User user(){
        User user = new User();
        user.setName("小马哥V5");
        return user;
    }
}
