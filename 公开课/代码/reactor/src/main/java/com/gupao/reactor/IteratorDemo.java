package com.gupao.reactor;



import java.util.Iterator;
import java.util.List;

/**
 * {@link Iterator} Demo
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-26
 **/
public class IteratorDemo {

    public static void main(String[] args) {

        // 数据源
        // 这里是 Java 9 的 API
        List<Integer> values = List.of(1, 2, 3, 4, 5);

        // 迭代
        // 消费数据
        Iterator<Integer> iterator = values.iterator();

        while (iterator.hasNext()) { // 这个过程就是向服务器请求是否还有数据
            Integer value = iterator.next(); // 主动获取数据
            System.out.println(value);
        }


    }

}
