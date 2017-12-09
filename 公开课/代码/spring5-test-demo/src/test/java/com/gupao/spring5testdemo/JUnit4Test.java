package com.gupao.spring5testdemo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit 4 测试
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/9
 */
public class JUnit4Test
//        extends TestCase
{

    protected void setUp() throws Exception {
        System.out.println("JUnit 3 方式：准备数据源");
    }

    protected void tearDown() throws Exception {
        System.out.println("JUnit 3 方式：准备数据源");
    }

    @Before
    public void prepare() {
        System.out.println("JUnit 4 方式：准备数据源");
    }

    @After
    public void destroy() {
        System.out.println("JUnit 4 方式：关闭数据源");
    }

    /**
     * JUnit 3 的测试方式
     */
    public void testHelloWorld2() {
        System.out.println("HelloWorld2");
    }

    /**
     * JUnit 4 的测试方式
     */
    @Test
    public void testHelloWorld() {
        System.out.println("HelloWorld");
    }

    /**
     * JUnit 4 的测试方式
     */
    @Test
    public void testValue() {
        System.out.println("Value");
    }

    @Test
    public void test100Times() {
        for (int i = 0; i < 100; i++) {
            Assert.assertTrue(i > -1);
        }
    }

}
