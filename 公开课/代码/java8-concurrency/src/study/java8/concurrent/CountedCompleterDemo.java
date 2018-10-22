package study.java8.concurrent;

import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;

public class CountedCompleterDemo {

    public static void main(String[] args) {

        Integer[] data = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        LongAccumulator longAccumulator = new LongAccumulator((a, b) -> a + b, 0);

        ForEach.forEach(data, new MyOperation<Integer>() {

            @Override
            public void apply(Integer value, ForEach<Integer> forEach) {

                longAccumulator.accumulate(value);
                System.out.printf("Current CountedCompleter[%d] applied value is %d on Thread[%s]!\n",
                        forEach.id,
                        value,
                        Thread.currentThread().getName());

            }
        });

        System.out.println(longAccumulator.get());

    }

    static interface MyOperation<E> {
        default void apply(E e, ForEach<E> forEach) {
            System.out.println(e);
        }
    }

    static class ForEach<E> extends CountedCompleter<Void> {

        private static final AtomicInteger sequence = new AtomicInteger(0);

        final E[] array;
        final MyOperation<E> op;
        final int lo, hi;
        final private int id;

        ForEach(CountedCompleter<?> p, E[] array, MyOperation<E> op, int lo, int hi) {
            super(p);
            this.array = array;
            this.op = op;
            this.lo = lo;
            this.hi = hi;
            this.id = sequence.incrementAndGet();
        }

        public static <E> void forEach(E[] array, MyOperation<E> op) {
            new ForEach<E>(null, array, op, 0, array.length).invoke();
        }

        public void compute() { // version 1
            if (hi - lo >= 2) {
                int mid = (lo + hi) >>> 1;
                setPendingCount(2); // must set pending count before fork
                new ForEach(this, array, op, mid, hi).fork(); // right child
                new ForEach(this, array, op, lo, mid).fork(); // left child
            } else if (hi > lo)
                op.apply(array[lo], this);

            tryComplete();

        }

        public void onCompletion(CountedCompleter countedCompleter) {
            System.out.printf("Current CountedCompleter[ ID : %d , ] is completion on Thread[%s]!\n", ((ForEach) countedCompleter).id,
                    Thread.currentThread().getName());

        }
    }
}
