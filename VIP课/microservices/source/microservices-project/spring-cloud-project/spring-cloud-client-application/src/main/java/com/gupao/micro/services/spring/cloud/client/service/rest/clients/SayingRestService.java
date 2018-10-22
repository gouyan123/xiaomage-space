package com.gupao.micro.services.spring.cloud.client.service.rest.clients;

import com.gupao.micro.services.spring.cloud.client.annotation.RestClient;
import com.gupao.micro.services.spring.cloud.client.controller.ClientController;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;

@RestClient(name = "${saying.rest.service.name}")
public interface SayingRestService {

    @GetMapping("/say")
    String say(@RequestParam("message") String message); // 请求参数和方法参数同名

    public static void main(String[] args) throws Exception {
        Method method = SayingRestService.class.getMethod("say", String.class);
        Parameter parameter = method.getParameters()[0];
        System.out.println(parameter.getName());

        parameter.isNamePresent();

        DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

        Stream.of(nameDiscoverer.getParameterNames(method)).forEach(System.out::println);
    }
}
