package com.gupao.spring5testdemo;

import org.junit.Test;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.mock.env.MockEnvironment;

/**
 * {@link Environment} Test
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/9
 */
public class EnvironmentTest {

    @Test
    public void testGetProperty(){
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("os.name","Windows 7");
        System.out.println(environment.getProperty("os.name"));
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("TMP"));
    }

    @Test
    public void testStandardEnvironment(){
        StandardEnvironment environment = new StandardEnvironment();
        System.out.println(environment.getProperty("TMP"));
    }

}
