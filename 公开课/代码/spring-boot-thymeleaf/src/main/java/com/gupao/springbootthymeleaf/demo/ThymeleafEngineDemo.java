package com.gupao.springbootthymeleaf.demo;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.context.Context;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import java.util.Properties;


/**
 * Thymeleaf 引擎
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/30
 */
public class ThymeleafEngineDemo {

    public static void main(String[] args) {
        // 构建引擎
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();

        Properties properties = new Properties();
        properties.setProperty("name_zh_CN", "小马哥");
        properties.setProperty("name_en", "小马哥");
        // 国际化 i18n
        StandardMessageResolver messageResolver = new StandardMessageResolver();
        messageResolver.setDefaultMessages(properties);
        templateEngine.setMessageResolver(messageResolver);
        // 模板解析
        // Spring Resource -> Thymeleaf Template Resource
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();

        ResourceLoader resourceLoader = new DefaultResourceLoader();

        String prefix = "classpath:/templates/thymeleaf/";
        String suffix = ".html";
        String returnValue = "index";

        Resource templateResource =
                resourceLoader.getResource(prefix + returnValue + suffix);

        // 创建渲染上下文
        Context context = new Context();
        context.setVariable("message", "Hello,World");
        // 模板的内容
        String content = "<p th:text=\"${message}\">!!!</p>";
        // 渲染（处理）结果
        String result = templateEngine.process(content, context);
        // 输出渲染（处理）结果
        System.out.println(result);
    }
}
