package com.gupao.designpatterndemos.flyweight;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/14
 */
public class StringInterningDemo {

    public static void main(String[] args) {

        String value = new String("Hello"); // 在 Heap
        String newValue = value.intern(); // 放置常亮池


    }
}
