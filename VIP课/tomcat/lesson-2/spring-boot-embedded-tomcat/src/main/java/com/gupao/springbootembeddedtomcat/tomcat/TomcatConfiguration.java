package com.gupao.springbootembeddedtomcat.tomcat;

import org.apache.catalina.core.StandardContext;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Tomcat 配置 Class
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/1/20
 */
@Configuration
public class TomcatConfiguration implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        System.err.println(container.getClass());

        if (container instanceof TomcatEmbeddedServletContainerFactory) {

            TomcatEmbeddedServletContainerFactory factory =
                    (TomcatEmbeddedServletContainerFactory) container;

            // 相当于 new TomcatContextCustomizer(){}
            factory.addContextCustomizers((context) -> { // Lambda
                if (context instanceof StandardContext) {
                    StandardContext standardContext = (StandardContext) context;
                    // standardContext.setDefaultWebXml(); // 设置
                    standardContext.setReloadable(true);
                }
            });

            // 相当于 new TomcatConnectorCustomizer() {}
            factory.addConnectorCustomizers(connector -> {
                connector.setPort(12345);
            });

        }

    }
}
