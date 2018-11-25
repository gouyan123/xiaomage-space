package com.gupao.oop.function.design;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * 累加求和
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/10
 */
public class SumDemo {

    // Java 8 面向函数计算方式
    // 通过面向对象的方式（Function 是接口）
    // 语法补充，你可以执行代码或者方法引用
    private static int sumInJava8(int size, BinaryOperator<Integer> function) {
        List<Integer> values = range(1, size);
        int sum = 0;
        return values.stream().reduce(sum, function);
    }

    public static void main(String[] args) {

        int sum = sumInJava8(10, Integer::sum);

        System.out.println(sum);

    }

    public static int calculate(int a, int b) {
        return a + b;
    }

    private interface Sum {

        /**
         * 计算方法
         *
         * @param a 左值
         * @param b 右值
         * @return 计算值
         */
        int calc(int a, int b);

    }

    // Java 5 面向过程计算方式
    private static int sumInJava5(int size) {
        List<Integer> values = range(1, size);
        //变量参数  (Java 5)
        int sum = 0;
        Iterable<Integer> integers = values; // 泛型 (Java 5)
        for (Integer value : integers) { // 迭代语句 Iterable 作为参数 (Java 5)
            sum += value; // Unboxing Integer -> int  (Java 5)，这里计算过程不是面向对象的
        }
        return sum;
    }

    // Java 5 面向对象计算方式
    private static int sumInJava5(int size,/*计算方法*/Sum sum) {
        List<Integer> values = range(1, size);
        //变量参数  (Java 5)
        int result = 0;
        Iterable<Integer> integers = values; // 泛型 (Java 5)
        for (Integer value : integers) { // 迭代语句 Iterable 作为参数 (Java 5)
            result = sum.calc(result, value); // Unboxing Integer -> int  (Java 5)
        }
        return result;
    }

    private static int sumInJava8(int size) {
        List<Integer> values = range(1, size);
        int sum = 0;

        // values = [1,2,3,4,5,6,7,8,9,10];
        // stream 流式
        // values = [(1,2),3,4,5,6,7,8,9,10];
        // => sum(1,2),3,4,5,6,7,8,9,10
        //  => sum(3,3),4,5,6,7,8,9,10
        // Stream API
        // 方法引用

        // using std;
        // std::cout => cout
        return values.stream().reduce(sum, Integer::sum);
    }




    private static List<Integer> range(int since, int count) {
        List<Integer> values = new ArrayList<Integer>(count);
        for (int i = since; i <= since + count; i++) {
            values.add(i);
        }
        return values;
    }
}
