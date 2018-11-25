package com.gupao.javaresourcedemo.classpath;

import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/16
 */
public class ClasspathDemo {

    public static void main(String[] args) throws Exception {
        // Spring Classpath protocol
        // classpath:/META-INF/license.txt
        // URL url = classLoader.getResource("META-INF/license.txt");
        URL url = new URL("classpath:/application.properties");

        URLConnection urlConnection = url.openConnection();

        InputStream inputStreamFromURL = urlConnection.getInputStream();

        String content = StreamUtils.copyToString(inputStreamFromURL, Charset.forName("UTF-8"));

        System.out.println(content);
    }
}
