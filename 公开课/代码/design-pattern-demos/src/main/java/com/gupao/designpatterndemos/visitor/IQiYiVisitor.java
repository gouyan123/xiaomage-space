package com.gupao.designpatterndemos.visitor;

/**
 * 爱奇艺访问者
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/17
 */
public class IQiYiVisitor implements Visitor {

    @Override
    public void visit(Login login) {
        System.out.println("爱奇艺访问者");
        login.accept(this);
    }
}
