package com.gupao.util;

/**
 * TODO:小马哥，写点注释吧！
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-26
 **/
public class Utils {

    public static void println(Object message) {
        System.out.printf("[Thread : %s ] :  %s \n",
                Thread.currentThread().getName(),
                String.valueOf(message)
        );
    }

}
