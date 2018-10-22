package com.gupao.design;

import java.util.LinkedList;
import java.util.List;

/**
 * 链式设计 示例
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/3
 */
public class ChainDemo {
    // 执行者
    // Chain

    public static void main(String[] args) {
        // 过滤器
        // 流水线 Pipeline
        // Stream 流式处理
        DefaultExecutorChain chain = new DefaultExecutorChain();

        chain.addExecutor(new Executor() { // 第一个节点
            @Override
            public void execute(ExecutorChain chain) {
                System.out.println("Hello,World");
                // 执行链条中的下一个节点
                chain.execute();
            }
        });

        chain.addExecutor(
                (c) -> {
                    System.out.println("ABC");
                }
        );

        // 执行执行链
        chain.execute();
    }


    public static class DefaultExecutorChain implements ExecutorChain {

        // [0] -> [1] -> [2]
        private final List<Executor> executorsList = new LinkedList();
        // 当前执行位置
        private int position = 0;

        // 增加链条上的节点
        public void addExecutor(Executor executor) {
            executorsList.add(executor);
        }

        @Override
        public void execute() {
            // position = 0 => position = 1
            int pos = position;
            Executor executor = executorsList.get(pos);
            System.out.println("执行第 " + pos + " 个 Executor 元素");
            position++;
            executor.execute(this);

        }
    }

    public interface Executor {

        void execute(ExecutorChain chain);

    }

    public interface ExecutorChain {

        void execute();

    }
}
