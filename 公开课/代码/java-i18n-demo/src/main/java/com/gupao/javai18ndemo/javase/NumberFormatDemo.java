package com.gupao.javai18ndemo.javase;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * {@link NumberFormat} 示例
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/4/14
 */
public class NumberFormatDemo {

    public static void main(String[] args) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        System.out.println(numberFormat.format(10000));

        numberFormat = NumberFormat.getNumberInstance(Locale.FRANCE);
        System.out.println(numberFormat.format(10000));
    }
}
