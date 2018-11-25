package java9.lang;

import java.util.stream.Stream;

import static java.lang.System.out;

/**
 * TODO:小马哥，写点注释吧！
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-20
 **/
public class StackFrameDemo {

    public static void main(String[] args) {

        // Java 9 前时代
        echoStackTraceElement();

        // Java 9 时代
        echoStackWalker();

    }

    private static void echoStackTraceElement() {
        System.out.println("echoStackTraceElement() : ");
        Stream.of(Thread.currentThread().getStackTrace()).forEach(out::println);
    }

    private static void echoStackWalker(){
        System.out.println("echoStackWalker() : ");
        StackWalker.getInstance().forEach(out::println);
    }
}
