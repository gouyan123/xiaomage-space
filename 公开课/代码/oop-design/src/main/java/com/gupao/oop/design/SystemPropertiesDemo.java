package com.gupao.oop.design;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/2/24
 */
public class SystemPropertiesDemo {

    public static void main(String[] args) {

        /**
         * System.getProperties() 尽可能地读一次
         * 由于 Properties 对象是线程安全的 Hashtable，读和写都是增加 synchronized
         * 可以参考 org.apache.commons.lang3.SystemUtils#FILE_ENCODING，通过常亮来读取
         * org.apache.commons.lang.SystemUtils#FILE_ENCODING
         */
        System.out.println(System.getProperty("file.encoding"));

        // Java 1.4 : NIO 1.4
        // Java 5 : Formatter
        // Java 6 : JS on JVM
        // Java 7 : Fork/Join NIO2
        // Java 8 : Lambda

        /**
         * 确认依赖版本
         *
         * 多查看 @since
         * commons-lang3 3.7
         * commons-lang3 3.5
         * 请注意二进制兼容（class /jar 兼容）
         * 在 Maven 构建系统中，减少二方库传递性依赖
         */
    }
}
