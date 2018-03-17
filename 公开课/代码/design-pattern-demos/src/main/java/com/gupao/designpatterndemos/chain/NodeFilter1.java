package com.gupao.designpatterndemos.chain;

import javax.servlet.*;
import java.io.IOException;

/**
 * NodeFilter1 -> Servlet1
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/17
 */
public class NodeFilter1 implements javax.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if(true){
            return; // 条件式阻断
        }

        // Before

        chain.doFilter(request,response); // 执行下一个节点:Servlet

        // After

    }

    @Override
    public void destroy() {

    }
}
