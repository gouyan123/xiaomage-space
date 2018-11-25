package com.gupao.micro.services.spring.cloud.stream.binder.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HttpMessageChannelBinderConfiguration {

//    /**
//     * 自动装配 {@link MessageReceiverController} Bean
//     *
//     * @return
//     */
//    @Bean
//    public MessageReceiverController messageReceiverController() {
//        return new MessageReceiverController();
//    }

    @Bean
    public HttpMessageChannelBinder httpMessageChannelBinder(
            DiscoveryClient discoveryClient,
            MessageReceiverController controller) {
        return new HttpMessageChannelBinder(discoveryClient, controller);
    }
}
