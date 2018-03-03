package com.gupao.design;

import java.util.function.Supplier;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/3
 */
public class LambdaDemo {

    public static void main(String[] args) {

        echo(() -> "Hello,World");

    }

    private static void echo(Supplier<?> supplier) {
        System.out.println(supplier.get());
    }

    @FunctionalInterface
    public interface Valuable<V> {

        V get();

    }

}
