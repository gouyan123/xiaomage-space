package com.gupao.designpatterndemos.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者模式Demo
 * <p>
 * Observable 是一个维护有序的 Observer 集合，Subject、Publisher
 * Observer  = Consumer = Subscriber
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/17
 */
public class ObserverDemo {

    public static void main(String[] args) {
        // JDK 存在观察者模式的实现

        MyObservable observable = new MyObservable();

        // 注册观察者
        observable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("张三：邮件订阅 :" + arg);
            }
        });

        observable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("李四：邮件订阅 :" + arg);
            }
        });
        observable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("王五：邮件订阅 :" + arg);
            }
        });

        // 调整变化
        observable.setChanged();

        // 通知变化到所有观察者
        observable.notifyObservers("邮件通知：Hello,World");

    }

    private static class MyObservable extends Observable {

        // 子类提升方法从 protected 到 public
        public void setChanged() {
            super.setChanged();
        }
    }

}
