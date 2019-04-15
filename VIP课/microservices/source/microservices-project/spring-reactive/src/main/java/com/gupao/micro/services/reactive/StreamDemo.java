package com.gupao.micro.services.reactive;

import java.util.stream.Stream;

public class StreamDemo {
    public static void main(String[] args) {
        Stream.of(0,1,2,3,4,5,6,7,8,9)              //生产数据
                .filter(v -> v % 2 == 0)            //Predicate 判断数据
                .map(v -> v + 1)                    //Function 改变数据维度
                .reduce(Integer::sum)               //聚合操作
                .ifPresent(System.out::println);    //Consumer 消费数据
//                .forEach(System.out::println);      //Consumer

    }
//    public static void main(String[] args) {
//
//        // 是不是非常直观
//        Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) // 0-9 集合
//                .filter(v -> v % 2 == 1) // 判断数值->获取奇数
//                .map(v -> v - 1) // 奇数变偶数
//                .reduce(Integer::sum) // 聚合操作
//                .ifPresent(System.out::println) // 输出 0 + 2 + 4 + 6 + 8
//
//                //.forEach(System.out::println); // 消费属性 Consumer
//
//        ;
//
//    }
}
