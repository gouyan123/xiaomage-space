package com.gupao.springbootbeanvalidation;

import com.gupao.springbootbeanvalidation.web.UserControllerInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringBootBeanValidationApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBeanValidationApplication.class, args);
	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserControllerInterceptor());
	}
}
