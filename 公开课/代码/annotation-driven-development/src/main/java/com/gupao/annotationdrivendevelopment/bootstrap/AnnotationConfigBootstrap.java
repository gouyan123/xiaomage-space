package com.gupao.annotationdrivendevelopment.bootstrap;


import com.gupao.annotationdrivendevelopment.config.UserConfiguration;
import com.gupao.annotationdrivendevelopment.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Annotation 配置引导程序
 * 广告资源位...
 *
 * @author mercyblitz
 * @date 2017-10-09
 **/
public class AnnotationConfigBootstrap {

    public static void main(String[] args) {
        // 构建一个 Spring Application 上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();

        // 需要注册一个UserConfiguration 的Bean
        applicationContext.register(UserConfiguration.class);

        applicationContext.refresh();

        User user = applicationContext.getBean("user", User.class);

        System.out.printf("user.getName() = %s \n",user.getName());



    }

}
