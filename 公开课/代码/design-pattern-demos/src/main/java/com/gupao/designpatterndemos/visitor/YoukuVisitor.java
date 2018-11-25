package com.gupao.designpatterndemos.visitor;

/**
 * 优酷访问者
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/17
 */
public class YoukuVisitor implements Visitor {

    @Override
    public void visit(Login login) {
        System.out.println("优酷访问者");
        login.accept(this);
    }
}
