package com.gupao.jsp.in.spring.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.servlet.Filter;

/**
 * Web 自动装配
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/1/14
 */
@EnableWebMvc
@ComponentScan("com.gupao.jsp.in.spring.web.controller")
@Configuration
public class WebAutoConfiguration extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
     * <property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
     * <property name="prefix" value="/WEB-INF/jsp/"/>
     * <property name="suffix" value=".jsp"/>
     * </bean>
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver
                = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }


    /**
     * <filter>
     * <filter-name>CharacterEncodingFilter</filter-name>
     * <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
     * <init-param>
     * <param-name>encoding</param-name>
     * <param-value>UTF-8</param-value>
     * </init-param>
     * <init-param>
     * <param-name>forceRequestEncoding</param-name>
     * <param-value>true</param-value>
     * </init-param>
     * <init-param>
     * <param-name>forceResponseEncoding</param-name>
     * <param-value>true</param-value>
     * </init-param>
     * </filter>
     * <p>
     * <filter-mapping>
     * <filter-name>CharacterEncodingFilter</filter-name>
     * <servlet-name>app</servlet-name>
     * </filter-mapping>
     */
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{characterEncodingFilter()};
    }

    private Filter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceRequestEncoding(true);
        filter.setForceResponseEncoding(true);
        return filter;
    }

    /**
     * 等价于 {@link ContextLoaderListener}
     *
     * @return
     */
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    /***
     * 相当于 DispatcherServlet 加载 WEB-INF/gupao.xml 文件
     * @return
     */
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebAutoConfiguration.class};
    }

    /**
     * <servlet-mapping>
     * <servlet-name>app</servlet-name>
     * <url-pattern>/</url-pattern>
     * </servlet-mapping>
     *
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected String getServletName() {
        return "app";
    }
}
