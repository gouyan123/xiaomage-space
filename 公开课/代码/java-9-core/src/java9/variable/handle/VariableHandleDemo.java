package java9.variable.handle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * TODO:小马哥，写点注释吧！
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-20
 **/
public class VariableHandleDemo {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        testVarHandle();
        testUnsafe();
    }

    private static void testVarHandle() throws NoSuchFieldException, IllegalAccessException {
        A a = new A();
//        a.value 从 "Hello" 调整为 "World"
        VarHandle varHandle = MethodHandles.lookup().findVarHandle(A.class, "value", String.class);

        // CAS = 首先要比较对等性，然后才能设置
        varHandle.compareAndSet(a,"Hell1","World");

        System.out.println(a.value);

        varHandle.compareAndSet(a,"Hello","World");

        System.out.println(a.value);

    }


    private static void testUnsafe() {
        // Unsafe.getUnsafe(); // 此处直接调用有问题！
    }

    private static class A {

        String value = "Hello";

    }
}
