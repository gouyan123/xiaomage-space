package com.gupao.javabeans;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Id 属性修改器
 * id long 类型
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/25
 */
public class IdPropertyEditor extends PropertyEditorSupport {

    public void setAsText(String text) {
        if (StringUtils.hasText(text)) {
            long id = Long.parseLong(text);
            setValue(id);
        } else {
            setValue(Long.MIN_VALUE);
        }


    }
}
