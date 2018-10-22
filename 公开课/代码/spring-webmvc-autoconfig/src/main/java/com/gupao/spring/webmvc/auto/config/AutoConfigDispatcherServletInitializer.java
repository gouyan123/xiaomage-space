package com.gupao.spring.webmvc.auto.config;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * TODO:小马哥，写点注释吧！
 * 广告资源位...
 *
 * @author mercyblitz
 * @date 2017-10-09
 **/
public class AutoConfigDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Nullable
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Nullable
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringWebMvcConfiguration.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/*"};
    }
}
