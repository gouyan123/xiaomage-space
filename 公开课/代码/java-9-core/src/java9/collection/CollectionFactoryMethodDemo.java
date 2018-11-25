package java9.collection;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.System.out;

/**
 * TODO:小马哥，写点注释吧！
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-20
 **/
public class CollectionFactoryMethodDemo {

    public static void main(String[] args) {
        Set<String> words = Set.of("Hello","World");
        words.forEach(out::println);

        Set<Integer> values =  of(1,2,3,4,5,6,7,8,9,10);

        Integer sum = values.stream().reduce(Integer::sum).get();

        System.out.println(sum);

        sum = values.stream().parallel().reduce(Integer::sum).get();

        System.out.println(sum);
    }

    private static <T> Set<T> of(T... values) {
        Set<T> set = new LinkedHashSet<>();
        // add
        Stream.of(values).forEach(value ->{
            set.add(value);
        });
        return set;
    }
}
