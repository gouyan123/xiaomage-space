package com.gupao.spring5testdemo;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Servlet API 测试
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/9
 */
public class ServletAPITest {

    @Test
    public void testHttpServletRequestInDynamicMock() {
        // 动态代理 HttpServletRequest
        HttpServletRequest request= mock(HttpServletRequest.class);
        // 当需要调用 HttpServletRequest#getParameter 时，并且参数名称为"name"
        when(request.getParameter("name")).thenReturn("小马哥");

        String value = request.getParameter("name");

        assertEquals("小马哥",value);
    }

    @Test
    public void testHttpServletRequestInStaticMock() {

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.setParameter("name","小马哥");
        // 获取 请求参数
        // 没有 Web 服务，也没有 Tomcat，也没有 Spring Boot
        String value = request.getParameter("name");

        assertEquals("小马哥",value);
    }

}
