package com.gupao.springcloudconfigclient.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**Spring 自定义事件/监听器；event可以获取上下文 event.getApplicationContext()*/
public class SpringEventListenerDemo {
    public static void main(String[] args) {
        // Annotation 驱动的 Spring 上下文
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        // 注册监听器，该监听器只关注 MyApplicationEvent这个事件；
        context.addApplicationListener(
                new ApplicationListener<MyApplicationEvent>() {
                    /**监听器得到事件；@param event*/
                    @Override
                    public void onApplicationEvent(MyApplicationEvent event) {
                        System.err.println("接收到事件：" + event.getSource() +" @ "+event.getApplicationContext());
                    }
                });
        // 刷新上下文 context
        context.refresh();
        // publishEvent()可以发布各种事件，只触发监听该事件的监听器；
        context.publishEvent(new MyApplicationEvent(context,"Hello,World"));
        context.publishEvent(new MyApplicationEvent(context,1));
        context.publishEvent(new MyApplicationEvent(context,new Integer(100)));
    }

    private static class MyApplicationEvent extends ApplicationEvent {
        private final ApplicationContext applicationContext;
        /**
         * Create a new ApplicationEvent.
         *
         * @param source the object on which the event initially occurred (never {@code null})
         */
        /**构造器必须传一个 事件源进来，这里事件源可以任意传*/
        public MyApplicationEvent(ApplicationContext applicationContext, Object source) {
            super(source);
            this.applicationContext=applicationContext;
        }

        public ApplicationContext getApplicationContext() {
            return applicationContext;
        }
    }
}
