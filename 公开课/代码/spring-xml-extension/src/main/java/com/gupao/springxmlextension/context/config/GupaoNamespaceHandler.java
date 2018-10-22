package com.gupao.springxmlextension.context.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/5/5
 */
public class GupaoNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("user",new UserBeanDefinitionParser());
    }
}
