package com.gupao.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/23
 */
public class DispatcherServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // message 渲染上下文
        request.setAttribute("message", "Hello,World");

        String path = request.getParameter("path");

        // forward -> index.jsp
        request.getRequestDispatcher(path)
                .forward(request, response);

    }

}
