package com.gupao.javaresourcedemo.file;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties Demo
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/16
 */
public class PropertiesDemo {

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // E:\Downloads\java-resource-demo\src\main\resources\application.properties
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String appName = properties.getProperty("spring.application.name");
        System.out.println(appName);
    }
}
