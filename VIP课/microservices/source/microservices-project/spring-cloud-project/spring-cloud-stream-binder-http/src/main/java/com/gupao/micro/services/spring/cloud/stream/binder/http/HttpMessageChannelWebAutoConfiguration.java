package com.gupao.micro.services.spring.cloud.stream.binder.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class HttpMessageChannelWebAutoConfiguration {

    @Bean
    public MessageReceiverController controller() {
        return new MessageReceiverController();
    }
//
//    @Bean
//    public MessageReceiverHandlerInterceptor interceptor() {
//        return new MessageReceiverHandlerInterceptor();
//    }
//
//    @Autowired
//    private MessageReceiverHandlerInterceptor interceptor;

//    @Configuration
//    public class MyWebMvcConfigurer implements WebMvcConfigurer {
//        public void addInterceptors(InterceptorRegistry registry) {
//            registry.addInterceptor(interceptor);
//        }
//    }


}
