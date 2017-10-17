package com.gupao.asyncweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.gupao.asyncweb.servlet")
public class AsyncWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncWebApplication.class, args);
	}
}
