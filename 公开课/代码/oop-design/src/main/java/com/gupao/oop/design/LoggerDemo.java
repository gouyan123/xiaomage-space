package com.gupao.oop.design;

import java.util.logging.Logger;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/2/24
 */
public class LoggerDemo {

    static {
        System.out.println("Hello");
    }

    public static void main(String[] args) {

        System.out.println("World");

        /**
         * 尽量利用 -Djava.util.logging.config.file 参数来初始化配置项
         * -D 等同 System.setProperty(name,value)
         */
        Logger logger = Logger.getLogger("com");

        logger.fine("Hello,World");
    }
}
