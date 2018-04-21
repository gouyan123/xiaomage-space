package com.gupao.studysourcecode.java.beans;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/4/21
 */
public class UserSpringDemo {

//    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
//        <property name="driverClassName" value="${jdbc.driverClassName}" />
//        <property name="url" value="${jdbc.url}" />
//        <property name="username" value="${jdbc.username}" />
//        <property name="password" value="${jdbc.password}" />
//    </bean>
    public static void main(String[] args) throws NamingException {

        // 没有DataSource 定义
        // User 定义
        DefaultListableBeanFactory parentBeanFactory = new DefaultListableBeanFactory();
        // 仅有 DataSource 定义
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // User 定义 又有 DataSource 定义
        beanFactory.setParentBeanFactory(parentBeanFactory);

        // xml
        DataSource user = (DataSource) beanFactory.getBean("dataSource",1L,"小马哥");

        // JNDI = Java Naming And Directory Interface
        Context context = new InitialContext();

        // Tomcat 容器
        Context context1 = (Context)context.lookup("java/com/");

        // java/com/gupao/DataSource ->
        DataSource bean = (DataSource)context1.lookup("gupao/DataSource");

    }
}
