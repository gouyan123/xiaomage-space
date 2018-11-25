package com.gupao.studysourcecode.java.beans;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.Constructor;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/4/21
 */
public class UserBeanSpringDemo {

    // XML 方式 Bean 定义 类型的描述，类的名称
    //    <bean id="user" class="com.gupao.studysourcecode.java.beans.User">
    //      <property name="id" value ="1" />
    //      <property name="name" value ="小马哥" />
    //    </bean>
    // Bean定义 -> Bean 类 -> Bean 实例化策略 -> 初始化（pre、post） -> Bean 存储
    // 早期（启动时）初始化、延迟（需要时）初始化 lazy-init="false"
    // 单例、原生（scope）
    public static void main(String[] args) throws Exception {

        ClassLoader classLoader = null;

        Class userClass = classLoader.loadClass("com.gupao.studysourcecode.java.beans.User");
        Constructor constructor = userClass.getConstructor();
        constructor.newInstance();

        // Bean 容器 IoC 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 理解 Bean 定义 = BeanDefinition
        // XML -> BeanDefinition
        // Annotation -> BeanDefinition
        // 编码方式 Bean 定义
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // User Bean 定义
        AbstractBeanDefinition userBeanDefinition = builder.getBeanDefinition();
        //
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        //  <property name="id" value ="1" />
        propertyValues.addPropertyValue("id", 1L);
        //  <property name="name" value ="小马哥" />
        propertyValues.addPropertyValue("name", "小马哥");
        userBeanDefinition.setPropertyValues(propertyValues);
        // 注册 BeanDefinition
        beanFactory.registerBeanDefinition("user", userBeanDefinition);

        User user = beanFactory.getBean(User.class);

        System.out.println(user);

    }
}
