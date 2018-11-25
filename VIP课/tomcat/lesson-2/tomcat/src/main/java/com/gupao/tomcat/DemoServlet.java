package com.gupao.tomcat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DemoServlet Servlet 3.0 写法
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/1/17
 */
@WebServlet(urlPatterns = "/demo")
public class DemoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String message = req.getParameter("message");

        resp.getWriter().println(message);

        resp.getWriter().println("<br />");

        String contextPath = req.getServletContext().getContextPath();

        resp.getWriter().println("Context Path : " + contextPath);

    }

}
