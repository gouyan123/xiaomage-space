package com.gupao.javai18ndemo.javase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@link DateFormat} 示例
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/4/14
 */
public class DateFormatDemo implements Runnable {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        new Thread(new DateFormatDemo()).start();
        new Thread(new DateFormatDemo()).start();
        new Thread(new DateFormatDemo()).start();
        new Thread(new DateFormatDemo()).start();
        new Thread(new DateFormatDemo()).start();
        new Thread(new DateFormatDemo()).start();
    }


    @Override
    public void run() { // 重进入 ReentrantLock

        System.out.println(dateFormat.format(new Date()));
    }
}
