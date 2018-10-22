package com.gupao.springxmlextension;

import com.gupao.springxmlextension.domain.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/5/5
 */
public class SpringDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("context.xml");

        User user = context.getBean("user1",User.class);
        System.out.println(user);

        user = context.getBean("user2",User.class);
        System.out.println(user);
    }
}
