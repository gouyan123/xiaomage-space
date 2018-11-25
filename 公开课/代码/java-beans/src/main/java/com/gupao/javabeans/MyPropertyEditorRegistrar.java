package com.gupao.javabeans;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import java.util.Date;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/25
 */
public class MyPropertyEditorRegistrar implements PropertyEditorRegistrar {
    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {

        // 将Date 类型 date 字段设置 PropertyEditor
        registry.registerCustomEditor(Date.class,"date",new DatePropertyEditor());
    }
}
