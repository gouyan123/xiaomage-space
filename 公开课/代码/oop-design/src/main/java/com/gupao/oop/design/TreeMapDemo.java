package com.gupao.oop.design;

import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/2/24
 */
public class TreeMapDemo {

    public static void main(String[] args) {

        // 可以排序
        // 排序有个规则：通过传递 Comparator 实例、Key 可以排序
        TreeMap<Comparable, Object> treeMap = new TreeMap<Comparable, Object>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return true;
            }
        });
        // Number（抽象类）：Integer、Long、Byte
        // Comparable（接口）：String、Number（Integer、Long、Byte）、ByteBuffer
        treeMap.put(Byte.valueOf((byte) 1), 1);
        treeMap.put(2, 2);
        treeMap.put(ByteBuffer.allocate(1), 2);

        echo(treeMap);

        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("Hello","World");
        echo(map);
    }

    private static void echo(Map<?, ?> map) {
        System.out.println(map);
    }
}
