package com.gupao.javabeans;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/25
 */
public class DatePropertyEditor extends PropertyEditorSupport {

    public void setAsText(String text){
        if(StringUtils.hasText(text)){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
            try {
                Date date = dateFormat.parse(text);
                setValue(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
