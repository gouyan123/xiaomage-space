package com.gupao.javabeans;

import java.beans.*;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * Java Beans 代码
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/25
 */
public class JavaBeansDemo {

    public static void main(String[] args) throws IntrospectionException, ClassNotFoundException {
        Class<?> beanClass = Class.forName("com.gupao.javabeans.User");
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass, Object.class);

        // Bean描述符（BeanDescriptor）
        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
        System.out.println(beanDescriptor);

        // 方法描述符（MethodDescriptor）
//        MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
//        Stream.of(methodDescriptors).forEach(System.out::println);

        // 属性描述符（PropertyDescriptor）
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        // 创建一个 User Bean
        User user = new User();

        Stream.of(propertyDescriptors).forEach(propertyDescriptor -> {

            String propertyName = propertyDescriptor.getName(); // id 或者 name
            if ("id".equals(propertyName)) { // 属性名称等于 "id" , 类型 long
                propertyDescriptor.setPropertyEditorClass(IdPropertyEditor.class);
                PropertyEditor propertyEditor = propertyDescriptor.createPropertyEditor(user);
                propertyEditor.addPropertyChangeListener(
                        new SetPropertyChangeListener(user,propertyDescriptor.getWriteMethod()));
                // 触发事件，同时事件源 propertyEditor
                propertyEditor.setAsText("99");
            } else if ("date".equals(propertyName)) { // date Date

                propertyDescriptor.setPropertyEditorClass(DatePropertyEditor.class);
                PropertyEditor propertyEditor = propertyDescriptor.createPropertyEditor(user);
                propertyEditor.addPropertyChangeListener(
                        new SetPropertyChangeListener(user,propertyDescriptor.getWriteMethod()));
                propertyEditor.setAsText("2017-11-25");
            }
        });

        System.out.println(user); // 输出 user
    }

    private static class SetPropertyChangeListener implements PropertyChangeListener {

        private final Object bean;
        private final Method setterMethod;

        private SetPropertyChangeListener(Object bean, Method setterMethod) {
            this.setterMethod = setterMethod;
            this.bean = bean;
        }

        @Override
        public void propertyChange(PropertyChangeEvent event) {
            PropertyEditor source = (PropertyEditor) event.getSource();
            try {
                setterMethod.invoke(bean, source.getValue());
            } catch (Exception e) {
            }
        }
    }


}
