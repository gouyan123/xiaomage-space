package com.gupao.spring;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class DefaultResourceLoaderDemo {

    public static void main(String[] args) throws IOException {

        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

        resourceLoader.addProtocolResolver((location, loader) -> {
            if (location.startsWith("gupao://"))
                return new ClassPathResource(location.substring("gupao://".length()));
            return null;
        });

        Resource resource = resourceLoader.getResource("gupao://application.properties");

        InputStream inputStream = resource.getInputStream();

        System.out.println(StreamUtils.copyToString(inputStream, Charset.forName("UTF-8")));

    }
}
