package com.gupao.javai18ndemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.gupao.javai18ndemo.javaee")
public class JavaI18nDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaI18nDemoApplication.class, args);
	}
}
