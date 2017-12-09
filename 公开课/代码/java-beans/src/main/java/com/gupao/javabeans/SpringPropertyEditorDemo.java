package com.gupao.javabeans;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring 理解 PropertyEditor
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/25
 */
public class SpringPropertyEditorDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        context.setConfigLocation("context.xml");
        context.refresh();

        User user = context.getBean("user",User.class);

        System.out.println(user);

    }
}
