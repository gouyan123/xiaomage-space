package com.gupao.designpatterndemos.visitor;

/**
 * 登录接口
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/17
 */
public interface Login {

    /**
     * 对于登录业务而言，访问者是被接受的
     * @param visitor
     */
    void accept(Visitor visitor);
}
