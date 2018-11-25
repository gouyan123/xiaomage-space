package com.gupao.javaresourcedemo.spring.resource;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/16
 */
public class ResourceDemo {

    public static void main(String[] args) throws IOException {
        // Resource
        // FileSystemResource
        // ClasspathResource
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        // 添加一个protocol = "cp" 处理
        resourceLoader.addProtocolResolver(new ProtocolResolver() {

            private static final String PROTOCOL_PREFIX = "cp:/";

            @Override
            public Resource resolve(String location, ResourceLoader resourceLoader) {
                if (location.startsWith(PROTOCOL_PREFIX)) {
                    // application.properties
                    String classpath = ResourceLoader.CLASSPATH_URL_PREFIX +
                            location.substring(PROTOCOL_PREFIX.length());
                    // cp:/application.properties -> classpath:application.properties
                    return resourceLoader.getResource(classpath);
                }
                return null;
            }
        });

        Resource resource =
                resourceLoader.getResource("cp:/application.properties");

        InputStream inputStream = resource.getInputStream();

        String content = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));

        System.out.println(content);

    }
}
