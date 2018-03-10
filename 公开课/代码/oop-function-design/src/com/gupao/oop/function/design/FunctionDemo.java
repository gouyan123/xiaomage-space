package com.gupao.oop.function.design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * 函数设计 Demo
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/10
 */
public class FunctionDemo {


    private static void demo(Supplier<List<Integer>> supplier) {
        // 1 - 10 累加并且把计算结果答应出来
        List<Integer> values = supplier.get();
        // 1 - 10 全部答应出来
        // Consumer -> 只接受数据，不返回（消费数据）
        // Supplier -> 没有接受数据，只有返回（提供数据），引用方法和构造器
        // Predicate -> 判断（1 - 10 挑选偶数）
        // Function -> 转换（Integer -> Integer * 2）
        // [1...10] => [2,4,6,8,10] => [4,8,12,16,20]
        values.stream()
                .filter(value -> value % 2 == 0) // Predicate：过滤偶数
                .map(value -> value * 2)         // Function：将偶数乘以2（convert）
                .forEach(System.out::println);   // Consumer
    }

    private static void demoFlatMap() {
        System.out.println(Arrays.asList("1,2", "3,4,5")
                .stream()
                // 一维变二维
                .map(value -> value.split(",")) // map
                // ["1,2","3,4,5"] -> [["1","2"],["3","4","5"]]
                .flatMap(values -> Arrays.stream(values))
                // 二维降到一维
                // [["1","2"],["3","4","5"]] => ["1","2","3","4","5"]
                .map(Integer::valueOf) // String -> Integer
                .reduce(0, Integer::sum)); // reduce

    }

    public static void main(String[] args) {
//        demo(FunctionDemo::oneToTen);
        demoFlatMap();
    }


    private static List<Integer> oneToTen() {
        return range(1, 10);
    }

    private static List<Integer> range(int since, int count) {
        List<Integer> values = new ArrayList<Integer>(count);
        for (int i = since; i < since + count; i++) {
            values.add(i);
        }
        return values;
    }
}
