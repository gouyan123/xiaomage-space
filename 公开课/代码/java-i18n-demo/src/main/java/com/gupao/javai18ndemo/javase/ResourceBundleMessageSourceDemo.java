package com.gupao.javai18ndemo.javase;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * {@link ResourceBundleMessageSource} 示例
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/4/14
 */
public class ResourceBundleMessageSourceDemo {

    public static void main(String[] args) {
        String baseName = "static.default";
        // ResourceBundle + MessageFormat => MessageSource
        // ResourceBundleMessageSource 不能重载
        // ReloadableResourceBundleMessageSource
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(baseName);
        System.out.println(messageSource
                .getMessage("message", new Object[]{"World"}, Locale.getDefault()));
    }
}
