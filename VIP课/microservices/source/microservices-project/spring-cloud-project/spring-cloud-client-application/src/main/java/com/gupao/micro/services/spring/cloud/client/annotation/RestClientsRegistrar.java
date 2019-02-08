package com.gupao.micro.services.spring.cloud.client.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

public class RestClientsRegistrar implements ImportBeanDefinitionRegistrar,
        BeanFactoryAware, EnvironmentAware {

    private BeanFactory beanFactory;

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata,
                                        BeanDefinitionRegistry registry) {

        ClassLoader classLoader = metadata.getClass().getClassLoader();

        Map<String, Object> attributes =
                metadata.getAnnotationAttributes(EnableRestClient.class.getName());

        // attributes -> { clients :  SayingRestService}
        Class<?>[] clientClasses = (Class<?>[]) attributes.get("clients");
        // 接口类对象数组
        // 筛选所有接口
        Stream.of(clientClasses)
                .filter(Class::isInterface) // 仅选择接口
                .filter(interfaceClass ->
                        findAnnotation(interfaceClass, RestClient.class) != null) // 仅选择标注 @RestClient
                .forEach(restClientClass -> {
                    // 获取 @RestClient 元信息
                    RestClient restClient = findAnnotation(restClientClass, RestClient.class);
                    // 获取 应用名称（处理占位符）
                    String serviceName = environment.resolvePlaceholders(restClient.name());

                    // RestTemplate -> serviceName/uri?param=...

                    // @RestClient 接口编程 JDK 动态代理
                    Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{restClientClass},
                            new RequestMappingMethodInvocationHandler(serviceName, beanFactory));

                    // 将 @RestClient 接口代理实现注册为 Bean（@Autowired）
                    // BeanDefinitionRegistry registry

                    String beanName = "RestClient." + serviceName;
                    // 实现方略二：SingletonBeanRegistry
                    if (registry instanceof SingletonBeanRegistry) {
                        SingletonBeanRegistry singletonBeanRegistry = (SingletonBeanRegistry) registry;
                        singletonBeanRegistry.registerSingleton(beanName, proxy);
                    }
//                    registerBeanByFactoryBean(serviceName,proxy,restClientClass,registry);
                });

    }

    private static void registerBeanByFactoryBean(String serviceName,
                                                  Object proxy, Class<?> restClientClass, BeanDefinitionRegistry registry) {

        String beanName = "RestClient." + serviceName;

        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(RestClientClassFactoryBean.class);

        /**
         *  <bean class="User">
         *          <constructor-arg>${}</construtor-arg>
         *      </bean>
         */
        // 增加第一个构造器参数引用 : proxy
        beanDefinitionBuilder.addConstructorArgValue(proxy);
        // 增加第二个构造器参数引用 : restClientClass
        beanDefinitionBuilder.addConstructorArgValue(restClientClass);

        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        registry.registerBeanDefinition(beanName, beanDefinition);

    }

    private static class RestClientClassFactoryBean implements FactoryBean {

        private final Object proxy;

        private final Class<?> restClientClass;

        private RestClientClassFactoryBean(Object proxy, Class<?> restClientClass) {
            this.proxy = proxy;
            this.restClientClass = restClientClass;
        }

        @Nullable
        @Override
        public Object getObject() throws Exception {
            return proxy;
        }

        @Nullable
        @Override
        public Class<?> getObjectType() {
            return restClientClass;
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
