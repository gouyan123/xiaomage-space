package com.gupao.springcloudbusdemo;

import com.gupao.springcloudbusdemo.bus.event.MessageRemoteApplicationEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@RemoteApplicationEventScan(basePackageClasses = MessageRemoteApplicationEvent.class)
public class SpringCloudBusDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudBusDemoApplication.class, args);
	}
}
