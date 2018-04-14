package com.gupao.javai18ndemo.javase;

import java.util.Locale;

/**
 * {@link Locale} 示例
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/4/14
 */
public class LocaleDemo {

    public static void main(String[] args) {
        // 安全 PropertyPermission
        // 通过启动参数调整 -D => System.setProperty("name","value");
        // 硬编码调整 en_US，无法做到一份，到处运行
        // Locale.setDefault(Locale.US);
        // 输入默认 Locale
        System.out.println(Locale.getDefault());
    }
}
