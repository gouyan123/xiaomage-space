package com.gupao.java9.modules.helloworld;


import java.util.logging.Logger;

/**
 * Hello World 引导程序
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/1/28
 */
public class HelloWorldMain {

    public static void main(String[] args) throws InterruptedException {
        // 默认情况只依赖的 java.base
        // 需要添加 java.logging
        Logger logger = Logger.getLogger(HelloWorldMain.class.getName());
        System.out.println("Hello,World");
        Thread.sleep(1000 * 3600);
    }
}
