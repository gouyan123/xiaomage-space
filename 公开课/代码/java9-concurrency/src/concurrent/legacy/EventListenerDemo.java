package concurrent.legacy;

import java.beans.PropertyChangeSupport;
import java.util.EventListener;
import java.util.EventObject;

/**
 * 事件监听者模式
 * {@link EventObject} 标准的事件对象 <br />
 * {@link EventListener} 标准的事件监听对象 <br />
 *
 * @author mercyblitz
 * @date 2017-09-29
 * @see EventListener
 **/
public class EventListenerDemo {

    public static void main(String[] args) {

        Person person = new Person();
        // Java Beans 规范 -> 内省
        // PropertyChangeEvent 广播源
        // PropertyChangeListener 注册器

        PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(person);

        //注册
        propertyChangeSupport.addPropertyChangeListener("name",
                (event) -> {
                    Person bean = (Person) event.getSource();
                    System.out.printf("Person[%s] 的name 属性，老值：%s，新值：%s"
                            , bean, event.getOldValue(), event.getNewValue());
                });

        // 触发事件
        propertyChangeSupport.firePropertyChange("name",
                null, "小马哥");


    }

    /**
     * POJO Setter/Getter
     */
    private static class Person {

        private String name;

        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }


}
