package com.gupao.designpatterndemos.visitor;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/17
 */
public class VisitorDemo {

    public static void main(String[] args) {
        // 创建优酷访问者
        Visitor visitor = new YoukuVisitor();
        // 微博登录
        Login login = new WeiboLogin();
        // 优酷访问者 -> 微博登录
        visitor.visit(login);

        // 创建爱奇艺的访问者
        visitor = new IQiYiVisitor();
        // 爱奇艺访问者 -> 微博登录
        visitor.visit(login);

        // QQ登录
        Login qqLogin = new QQLogin();
        // 爱奇艺访问者 -> QQ登录
        visitor.visit(qqLogin);

    }
}
