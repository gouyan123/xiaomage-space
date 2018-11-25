package com.gupao.vertx.core;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * Vertx Web Demo
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/2/4
 */
public class VertxWebDemo {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        // 路由对象
        Router router = Router.router(vertx);

        // 处理 /echo 请求
        router.get("/echo").handler(context -> {
            // 请求
            HttpServerRequest request = context.request();
            String message = request.getParam("message");
            // 响应
            HttpServerResponse response = context.response();
            response.end("Hello, " + message,"UTF-8");
        });

        // 创建 Http Server
        vertx.createHttpServer().requestHandler(router::accept).listen(8080); // 监听 8080 端口
    }
}
