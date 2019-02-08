package com.gupao.micro.services.spring.cloud.client.controller;

import com.gupao.micro.services.spring.cloud.client.annotation.CustomizedLoadBalanced;
import com.gupao.micro.services.spring.cloud.client.loadbalance.LoadBalancedRequestInterceptor;
import com.gupao.micro.services.spring.cloud.client.service.feign.clients.SayingService;
import com.gupao.micro.services.spring.cloud.client.service.rest.clients.SayingRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ClientController {

    @Autowired // 依赖注入自定义 RestTemplate Bean
    @CustomizedLoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    @LoadBalanced // 依赖注入 Ribbon RestTemplate Bean
    private RestTemplate lbRestTemplate;

    @Value("${spring.application.name}")
    private String currentServiceName;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private SayingService sayingService;

    @Autowired
    private SayingRestService sayingRestService;

    // 线程安全
//    private volatile Set<String> targetUrls = new HashSet<>();

//    @Scheduled(fixedRate = 10 * 1000) // 10 秒钟更新一次缓存
//    public void updateTargetUrlsCache() { // 更新目标 URLs
//        // 获取当前应用的机器列表
//        // http://${ip}:${port}
//        Set<String> oldTargetUrls = this.targetUrls;
//        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(currentServiceName);
//        Set<String> newTargetUrls = serviceInstances
//                .stream()
//                .map(s ->
//                        s.isSecure() ?
//                                "https://" + s.getHost() + ":" + s.getPort() :
//                                "http://" + s.getHost() + ":" + s.getPort()
//                ).collect(Collectors.toSet());
//
//        // swap
//        this.targetUrls = newTargetUrls;
//        oldTargetUrls.clear();
//    }

//    // Map Key service Name , Value URLs
//    private volatile Map<String, Set<String>> targetUrlsCache = new HashMap<>();
//
//    @Scheduled(fixedRate = 10 * 1000) // 10 秒钟更新一次缓存
//    public void updateTargetUrlsCache() { // 更新目标 URLs
//        // 获取当前应用的机器列表
//        // http://${ip}:${port}
//        Map<String, Set<String>> newTargetUrlsCache = new HashMap<>();
//        discoveryClient.getServices().forEach(serviceName -> {
//            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
//            Set<String> newTargetUrls = serviceInstances
//                    .stream()
//                    .map(s ->
//                            s.isSecure() ?
//                                    "https://" + s.getHost() + ":" + s.getPort() :
//                                    "http://" + s.getHost() + ":" + s.getPort()
//                    ).collect(Collectors.toSet());
//            newTargetUrlsCache.put(serviceName, newTargetUrls);
//        });
//
//        this.targetUrlsCache = newTargetUrlsCache;
//    }

    @GetMapping("/invoke/{serviceName}/say") // -> /say
    public String invokeSay(@PathVariable String serviceName,
                            @RequestParam String message) {

        // 自定义 RestTemplate 发送请求到服务器
        // 输出响应
        return restTemplate.getForObject("/" + serviceName + "/say?message=" + message, String.class);
    }

    @GetMapping("/lb/invoke/{serviceName}/say") // -> /say
    public String lbInvokeSay(@PathVariable String serviceName,
                              @RequestParam String message) {
        // Ribbon RestTemplate 发送请求到服务器
        // 输出响应
        return lbRestTemplate.getForObject("http://" + serviceName + "/say?message=" + message, String.class);
    }

    @GetMapping("/feign/say")
    public String feignSay(@RequestParam String message) {
        return sayingService.say(message);
    }

    @GetMapping("/rest/say")
    public String restSay(@RequestParam String message) {
        return sayingRestService.say(message);
    }

//    @GetMapping("/invoke/say") // -> /say
//        public String invokeSay(@RequestParam String message) {
//            // 服务器列表快照
//            List<String> targetUrls = new ArrayList<>(this.targetUrls);
//            int size = targetUrls.size();
//            // size =3 , index =0 -2
//            int index = new Random().nextInt(size);
//            // 选择其中一台服务器
//            String targetURL = targetUrls.get(index);
//            // RestTemplate 发送请求到服务器
//            // 输出响应
//            return restTemplate.getForObject(targetURL + "/say?message=" + message, String.class);
//    }

    @Bean
    public ClientHttpRequestInterceptor interceptor() {
        return new LoadBalancedRequestInterceptor();
    }

    // Ribbon RestTemplate Bean
    @LoadBalanced
    @Bean
    public RestTemplate loadBalancedRestTemplate() {
        return new RestTemplate();
    }

    // 自定义 RestTemplate Bean
    @Bean
    @Autowired
    @CustomizedLoadBalanced
    public RestTemplate restTemplate() { // 依赖注入
        return new RestTemplate();
    }

    @Bean
    @Autowired
    public Object customizer(@CustomizedLoadBalanced Collection<RestTemplate> restTemplates,
                             ClientHttpRequestInterceptor interceptor) {
        restTemplates.forEach(r -> {
            r.setInterceptors(Arrays.asList(interceptor));
        });
        return new Object();
    }

}
