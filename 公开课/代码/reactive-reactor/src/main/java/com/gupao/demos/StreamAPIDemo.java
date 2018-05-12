package com.gupao.demos;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class StreamAPIDemo {

    public static void main(String[] args) {

        // Stream => 1 -> 2 -> 3 ->4 ->5 (串行)
        // Stream 管道
        // Stream 运算模式
        //          串行
        Stream.of(1, 2, 3, 4, 5).forEach(System.out::println);
        System.out.println("===========================");
        //          并行
        Stream.of(1, 2, 3, 4, 5).parallel().forEachOrdered(System.out::println);

        // 包含 5 个元素
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5);

        // Iterator 包含 5 个元素
        Iterator<Integer> iterator = values.iterator();

        while (iterator.hasNext()) { // 被动的内容
            Integer value = iterator.next(); // 客户端主动请求
            if (value % 2 == 0) { // 判断是否为偶数
                System.out.println(value);
            } else{
                // 奇数仍然产生了
            }
        }

    }

    // 服务端推送数据 1 -> 2 -> 3 -> 4 -> 5
    // 客户端只要 1 -> 2 -> 3
    // 客户端 cancel 命令通知服务端不要推送 4 5
    public void onUpdate(Integer value) {
        if(value==3){
//            request.cancel();
        }
    }
}
