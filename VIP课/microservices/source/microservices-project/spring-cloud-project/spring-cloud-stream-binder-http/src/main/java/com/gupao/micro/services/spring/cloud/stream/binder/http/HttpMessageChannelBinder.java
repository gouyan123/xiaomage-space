package com.gupao.micro.services.spring.cloud.stream.binder.http;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

/**
 * HTTP {@link MessageChannel} {@link Binder}
 */
public class HttpMessageChannelBinder implements
        Binder<MessageChannel, ConsumerProperties, ProducerProperties> {

    private static final String TARGET_APP_NAME = "spring-cloud-server-application";

    private final DiscoveryClient discoveryClient;

    private final MessageReceiverController controller;

    public HttpMessageChannelBinder(DiscoveryClient discoveryClient,
                                    MessageReceiverController controller) {
        this.discoveryClient = discoveryClient;
        this.controller = controller;
    }

    /**
     * 随机负载均衡算
     * @param serviceName
     * @return
     */
    private ServiceInstance chooseServiceInstanceRandomly(String serviceName) {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
        int size = serviceInstances.size();
        int index = new Random().nextInt(size);
        return serviceInstances.get(index);
    }

    private String getTargetRootURL(String serviceName) {
        ServiceInstance serviceInstance = chooseServiceInstanceRandomly(serviceName);
        return serviceInstance.isSecure() ?
                "https://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() :
                "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
    }


    @Override
    public Binding<MessageChannel> bindConsumer(String name,
                                                String group, MessageChannel inputChannel,
                                                ConsumerProperties consumerProperties) {

        // 给 controller 注入 MessageChannel
        controller.setInputChannel(inputChannel);

        return null;
    }

    @Override
    public Binding<MessageChannel> bindProducer(String name,
                                                MessageChannel outputChannel,
                                                ProducerProperties producerProperties) {

        RestTemplate restTemplate = new RestTemplate();

        SubscribableChannel subscribableChannel = (SubscribableChannel) outputChannel;
        // 消息订阅回调
        subscribableChannel.subscribe(message -> { // 消息来源

            // POST 请求（写数据）
            // 消息体
            byte[] messageBody = (byte[]) message.getPayload();
            // HTTP 体

            // 对象的服务名称 -> IP:port 集合（列表）

            String rootURL = getTargetRootURL(TARGET_APP_NAME);
            // Endpoint URI : /message/receive
            // Target URL = rootURL + Endpoint URI  -> http://localhost:9090/mesage/receive
            String targetURI = rootURL + MessageReceiverController.ENDPOINT_URI;
//            // 消息头
//            MessageHeaders messageHeaders = message.getHeaders();
//            // HTTP 头

            // 请求实体 = POST 方法
            try {
                RequestEntity requestEntity = new RequestEntity(messageBody, HttpMethod.POST, new URI(targetURI));
                // 成功后，返回"OK"
                restTemplate.exchange(requestEntity, String.class);
            } catch (URISyntaxException e) {
            }

        });
        return null;
    }
}
