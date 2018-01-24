package com.gupao.springbootembeddedtomcat;

import com.gupao.springbootembeddedtomcat.tomcat.TomcatConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringBootEmbeddedTomcatApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(SpringBootEmbeddedTomcatApplication.class,
						TomcatConfiguration.class)
				.run(args);

	}
}
