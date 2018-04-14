package com.gupao.javai18ndemo.javase;

import java.util.ResourceBundle;
import java.util.spi.ResourceBundleControlProvider;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/4/14
 */
public class EncodingResourceBundleControlProvider implements
        ResourceBundleControlProvider {

    @Override
    public ResourceBundle.Control getControl(String baseName) {
        return new ResourceBundleDemo.EncodedControl();
    }
}
