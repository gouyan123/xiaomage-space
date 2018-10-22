package com.gupao.springcloudbusdemo;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * XML 形式 ApplicationContext Demo
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/24
 */
public class XmlApplicationContextDemo {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext xmlApplicationContext
                = new ClassPathXmlApplicationContext();

        xmlApplicationContext.setConfigLocation("META-INF/spring-context.xml");

        xmlApplicationContext.addApplicationListener(event -> {

            if (event instanceof PayloadApplicationEvent) {
                PayloadApplicationEvent payloadApplicationEvent = (PayloadApplicationEvent) event;
                System.err.println(payloadApplicationEvent.getPayload());
            } else {
                System.err.println(event);
            }

        });
        // 启动上下文
        xmlApplicationContext.refresh();
        // Spring 上下文是一个事件发布器，非 ApplicationEvent，是 PayloadApplicationEvent
        xmlApplicationContext.publishEvent("Hello,World");
        xmlApplicationContext.publishEvent(12345);
        xmlApplicationContext.publishEvent(xmlApplicationContext);


    }


}
