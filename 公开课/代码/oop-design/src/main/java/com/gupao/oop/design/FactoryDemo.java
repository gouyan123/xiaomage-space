package com.gupao.oop.design;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ThreadFactory;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/2/24
 */
public class FactoryDemo {

    public static void main(String[] args) {

        // 工厂模式：抽象工厂 + 静态工厂 ( GoF23 )
        // 并不是某种命名，一般建议通过 new create 前缀
        // 工厂模式：状态和无状态，可变和不可变
        Set<String> set = Set.of("Hello"); // 不变的
        ThreadFactory factory = (runnable) -> new Thread(runnable);

        Thread thread = factory.newThread(() -> {
            System.out.println("Hello,World");
        });

        // Integer : Number -> private final int value;
        // MutableInt : Number -> private int value;
        // 不变必然每次创建新对象，是否存在必要性需要讨论
        LocalDateTime localDateTime = LocalDateTime.of(2018, 2, 24, 22, 00);


    }
}
