package com.gupao.user.test;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/10/29
 */
public class RestTemplateDemo {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
//        Map<String,Object> data = restTemplate.getForObject("http://localhost:8080/env",Map.class);
//        System.out.println(data);
        //String 表示请求返回类型；
        System.out.println(restTemplate.getForObject("http://localhost:8080/env",String.class));
    }

}
