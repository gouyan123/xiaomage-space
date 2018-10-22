package com.gupao.jspinspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.gupao.jsp.in.spring.web.controller")
public class JspInSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(JspInSpringBootApplication.class, args);
	}
}
