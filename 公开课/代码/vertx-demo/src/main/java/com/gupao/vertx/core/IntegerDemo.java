package main.java.com.gupao.vertx.core;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/2/24
 */
public class IntegerDemo {
    public static void main(String[] args) {
        Integer value = 99;
        Integer value2 = new Integer(99);
        Integer value3 = Integer.valueOf(99);

        System.out.println("value == value2" + value.equals(value2));

        System.out.println("value == value3" + value.equals(value3));
    }
}
