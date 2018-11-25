package com.gupao.design;

import java.util.Optional;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/3
 */
public class OptionalDemo {

    public static void main(String[] args) {

        // 规避空指针问题
        Optional<String> optional = Optional.of("123"); // "123"

        // 流式写法
        System.out.println(
                optional.map(Double::valueOf) // 123.0

                // Map/Reduce

        );

    }
}
