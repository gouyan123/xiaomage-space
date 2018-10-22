/**
 * 用户服务模块
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/1/28
 */
module user.service {

    // 依赖
    // transitive 传递 requires
    requires transitive user.domain;
    requires java.logging;
    requires slf4j.api;

    // 依赖未经模块化的 Java 9 之前的 jar


    // 导出
    // 1. 本模块的 public class 需要显示地 exports
    // 2. exports 不支持子包传递
    exports com.gupao.service;
    exports com.gupao.service.impl;

}