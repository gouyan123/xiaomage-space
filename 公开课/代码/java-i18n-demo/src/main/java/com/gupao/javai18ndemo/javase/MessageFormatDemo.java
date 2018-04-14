package com.gupao.javai18ndemo.javase;

import java.text.MessageFormat;

/**
 * {@link MessageFormat} 示例
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/4/14
 */
public class MessageFormatDemo {

    public static void main(String[] args) {
        // Formatter
        MessageFormat format = new MessageFormat("Hello,{0},{1}!");
        System.out.println(format.format(new Object[]{"World","Gupao"}));
    }
}
