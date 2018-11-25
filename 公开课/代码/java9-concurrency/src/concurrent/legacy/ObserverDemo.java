package concurrent.legacy;

import java.util.Observable;

/**
 * 观察者模式 Demo
 *
 * @author mercyblitz
 * @date 2017-09-29
 **/
public class ObserverDemo {

    public static void main(String[] args) {

        // 第一个缺点：Vector 作为底层存储（全线程安全的）
        // 第二个缺点：没有支持泛型
        // 第三个缺点：同步 -> 阻塞
        MyObservable observable = new MyObservable(); // 构建观察对象

        // 增加了第一个观察者
        observable.addObserver((ob, value) -> { // int
            System.out.printf("观察到的数值：%s\n",value);
        });

        // 增加了第二个观察者
        observable.addObserver((ob, value) -> { // int
            System.out.printf("第二个观察者-收到了：%s\n",value);
        });

        // 设置变化值
        observable.setChanged();

        // 通知所有的观察者
        observable.notifyObservers(1234567);

    }

    private static class MyObservable extends Observable {

        public void setChanged(){
            super.setChanged();
        }

    }

}