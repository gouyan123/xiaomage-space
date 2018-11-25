package com.gupao.asyncweb.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 访问 "/simple/async"
 */
@WebServlet(value = "/simple/async", asyncSupported = true)
public class SimpleAsyncServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");

        PrintWriter writer = response.getWriter();

        writer.println(Thread.currentThread().getName() + " 开始执行！");

        // 启动异步上下文
        AsyncContext asyncContext = request.startAsync();

        asyncContext.addListener(new AsyncListener() {

            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                writer.println(Thread.currentThread().getName() + " 请求完成了！");


            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {

                writer.println(Thread.currentThread().getName() + " 请求超时了！");
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                writer.println(Thread.currentThread().getName() + " 请求错误了！");
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                writer.println(Thread.currentThread().getName() + " 异步请求开始！");
            }
        });


        //同步方式
        // asyncContext.complete();
        // 异步方式
        asyncContext.start(()->{
            writer.println(Thread.currentThread().getName() + " 执行中！");
            asyncContext.complete();
        });

    }

}
