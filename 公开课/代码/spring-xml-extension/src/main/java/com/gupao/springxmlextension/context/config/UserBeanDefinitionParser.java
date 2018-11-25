package com.gupao.springxmlextension.context.config;

import com.gupao.springxmlextension.domain.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.lang.Nullable;
import org.w3c.dom.Element;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/5/5
 */
public class UserBeanDefinitionParser implements BeanDefinitionParser {

    @Nullable
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        // XML 配置: <gupao:user id="1" name="${name}" />
        // 原始 String 类型 -> Long
        String id = element.getAttribute("id");
        // String 类型 -> Placeholder ${name}
        String name = element.getAttribute("name");
        String beanName = element.getAttribute("bean-name");

        // BeanDefinition -> BeanDefinitionBuilder
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 添加 Property 值，来自于 XML 配置
        builder.addPropertyValue("id", id);
        builder.addPropertyValue("name", name);
        // 创建 BeanDefinition
        BeanDefinition beanDefinition =  builder.getBeanDefinition();

        // 从 parserContext 获取 Bean 注册器
        BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();
        // 注册 Bean 定义
        beanDefinitionRegistry.registerBeanDefinition(beanName,beanDefinition);

        return beanDefinition;
    }
}
