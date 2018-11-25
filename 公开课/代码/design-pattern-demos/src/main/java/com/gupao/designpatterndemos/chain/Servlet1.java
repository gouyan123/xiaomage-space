package com.gupao.designpatterndemos.chain;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/17
 */
public class Servlet1 extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        // +1
        // +2

        // save() -> Save Node
        // update() -> Update Node ( Servlet 2)
        // delete() -> Delete Node

        // SaveFilter -> UpdateFilter -> Servlet 1
        // UpdateFilter -> Servlet 2

    }
}
