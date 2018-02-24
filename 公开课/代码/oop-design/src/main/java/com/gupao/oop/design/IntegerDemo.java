package com.gupao.oop.design;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/2/24
 */
public class IntegerDemo {

    public static void main(String[] args) {

        /**
         * private final int value;
         * final 修饰的字段符合 Java 内存模型
         * Effective Java
         */
        Integer value = 99;
        Integer value2 = new Integer(99);
        Integer value3 = Integer.valueOf(99);

        System.out.println("value == value2 " + (value == value2));

        System.out.println("value == value3 " + (value == value3));
    }
}
