package com.gupao.oop.design;

import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/2/24
 */
public class PropertiesDemo {

    public static void main(String[] args) throws IOException {

        Properties properties = new Properties();

        InputStream inputStream =
                PropertiesDemo.class.getResourceAsStream("/default.properties");
        // 引起乱码
        // properties.load(inputStream);

        // 好的面向对象设计通常存在方法重载，提供不同的来源渠道
        // 适配器模式（前者转换后者，然后适配后者接口） InputStream -> Reader\
        // 装饰器模式（两者具备相同的父接口） InputStream -> ByteInputStream
        // InputStreamReader 三个重载构造器
        InputStreamReader reader = new InputStreamReader(inputStream, SystemUtils.FILE_ENCODING);
        // 入参尽可能地保证接口或者抽象
        properties.load(reader);

        System.out.println(properties.get("name"));

    }
}
