package study.java8.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.LongAccumulator;

public class ForkJoinDemo {

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        LongAccumulator accumulator = new LongAccumulator((left, right) -> {
            return left+right;
        },0);

        List<Long> params = Arrays.asList(1L,2L,3L,4L,5L,6L,7L,8L,9L);

        forkJoinPool.invoke(new LongSumTask(params,accumulator));

        System.out.println(accumulator.get());
    }

    static class LongSumTask extends RecursiveAction {

        private final List<Long> elements;

        private final LongAccumulator accumulator;

        LongSumTask(List<Long> elements, LongAccumulator accumulator) {
            this.elements = elements;
            this.accumulator = accumulator;
        }

        @Override
        public void compute() {

            int size = elements.size();

            int parts = size / 2;

            if(size > 1){

                List<Long> left = elements.subList(0,parts);
                List<Long> right = elements.subList(parts,size);

                new LongSumTask(left,accumulator).fork().join();
                new LongSumTask(right,accumulator).fork().join();

            }else{

                if(elements.isEmpty()){
                    return;
                }

                Long num = elements.get(0);
                accumulator.accumulate(num);

            }

        }

    }


}
