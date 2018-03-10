package com.gupao.oop.function.design;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/10
 */
public class CharSequenceDemo {

    public static void main(String[] args) {

        String value = "Hello,World";

        CharSequence charSequence = value; // String <- CharSequence

        echo(charSequence);

        echo(value);


    }

    private static void echo(Object value) {
        System.out.println(value);
    }
}
