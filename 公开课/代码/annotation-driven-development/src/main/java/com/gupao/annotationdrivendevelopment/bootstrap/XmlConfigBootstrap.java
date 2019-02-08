package com.gupao.annotationdrivendevelopment.bootstrap;


import com.gupao.annotationdrivendevelopment.domain.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * XML 配置引导程序
 * 广告资源位...
 *
 * @author mercyblitz
 * @date 2017-10-09
 **/
public class XmlConfigBootstrap {
    public static void main(String[] args) {
        // 构建一个 Spring Application 上下文
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        applicationContext.setConfigLocations("classpath:/META-INF/spring/context.xml");
        applicationContext.refresh();
        User user = applicationContext.getBean("user", User.class);
        System.out.printf("user.getName() = %s \n",user.getName());
    }
}
