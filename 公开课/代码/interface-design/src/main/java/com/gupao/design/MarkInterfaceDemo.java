package com.gupao.design;

import java.io.Serializable;

/**
 * 标记（类属）接口
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/3
 */
public class MarkInterfaceDemo {

    public static void main(String[] args) {
        // 序列化
        // Hibernate / JPA
        // 实体（Entity） 实现序列化 Serializable
        // PK（Primary Key）也需要序列化 Serializable
        // PK（Primary Key）的类型
        //      数值（Long、Integer、Short、Double、Float）
        //      字符类型（String）

        Serializable value = new Long(1L);
        value = new Integer(1);
        value = new Short((short) 1);
        value = new Double(1);
        value = new Float(1);
        value = new String("Hello,World");

        save(1);
        save(1L);
        save("Hello,World");
    }

    public static void save(Serializable value) {
        System.out.println("save : " + value);
    }

}
