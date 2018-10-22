package com.gupao.javaresourcedemo.http;

import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/16
 */
public class HttpDemo {

    public static void main(String[] args) throws Exception {
        //URL  http://start.spring.io/
        // Spring RestTemplate
//        RestTemplate restTemplate = new RestTemplate();
//
//        InputStream inputStreamFromRestTemplate =
//                restTemplate.execute("http://start.spring.io/",
//                        HttpMethod.GET,
//                        request -> {
//                        },
//                        response -> {
//                            return response.getBody();
//                        }
//                );

        URL url = new URL("http://start.spring.io/");

        InputStream inputStreamFromURL = url.openStream();

        String content = StreamUtils.copyToString(inputStreamFromURL, Charset.forName("UTF-8"));

        System.out.println(content);

    }
}
