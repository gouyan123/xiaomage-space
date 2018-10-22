package com.gupao.javaresourcedemo.jar;

import org.springframework.context.ApplicationContext;

import java.net.URL;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/16
 */
public class JarDemo {

    public static void main(String[] args) {
        ClassLoader classLoader = ApplicationContext.class.getClassLoader();
        URL url = classLoader.getResource("META-INF/license.txt");
        System.out.println(url);
    }
}
