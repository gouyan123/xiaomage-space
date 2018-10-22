package com.gupao.oop.function.design;

/**
 * 面向对象编程
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/10
 */
public class OopDemo {

    public static void main(String[] args) {

        // 面向过程
        int value = 10;
        // 数据（状态的变化）不在类，没有封装
        value += 1;

        Int i = new Int();
        // + 2
        // 所有的行为都有方法来描述
        i.add(2);

        System.out.println(i.value);

        // Utils 方法都是面向过程的
        // GoF23
        // 常见：工厂、抽象工厂、单例模式（JVM 级别、ClassLoader 级别、容器级别）、共享（享元）模式、代理模式（静态、动态）、委派者模式

        // 常用：装饰器模式、策略模式、模板模式、创建者模式、观察者模式、责任链模式

        // 陌生：访问者模式、调停者模式、桥接模式
    }

    private static class Int {

        // 数据
        private int value;

        // 加 数据
        private void add(int data) {
            this.value += data;
        }

    }
}
