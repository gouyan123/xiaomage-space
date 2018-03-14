package com.gupao.designpatterndemos.decorator;

import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/14
 */
public class DecoratorDemo {

    public static void main(String[] args) {
        // 被装饰者
        InputStream inputStream = null;

        // 装饰者
        FilterInputStream filterInputStream =
                new DataInputStream(inputStream);

        // DataInputStream <- FilterInputStream <- InputStream
        // DataInputStream(InputStream)



    }
}
