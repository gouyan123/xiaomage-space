package com.gupao.springcloudhystrixclientdemo.controller;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Hystrix Demo Controller
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/1
 */
@RestController
public class HystrixDemoController {

    private final static Random random = new Random();

    /**
     * 当{@link #helloWorld} 方法调用超时或失败时，
     * fallback 方法{@link #errorContent()}作为替代返回
     *
     * @return
     */
    @GetMapping("hello-world-2")
    public String helloWorld2() {
        return new HelloWorldCommand().execute();
    }

    /**
     * 编程方式
     */
    private class HelloWorldCommand extends com.netflix.hystrix.HystrixCommand<String> {

        protected HelloWorldCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("HelloWorld"),
                    100);


        }

        @Override
        protected String run() throws Exception {
            // 如果随机时间 大于 100 ，那么触发容错
            int value = random.nextInt(200);

            System.out.println("helloWorld() costs " + value + " ms.");

            Thread.sleep(value);

            return "Hello,World";
        }

        //容错执行
        protected String getFallback() {
            return HystrixDemoController.this.errorContent();
        }
    }

    /**
     * 当{@link #helloWorld} 方法调用超时或失败时，
     * fallback 方法{@link #errorContent()}作为替代返回
     *
     * @return
     */
    @GetMapping("hello-world")
    @HystrixCommand(
            fallbackMethod = "errorContent",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "100")
            }

    )
    public String helloWorld() throws Exception {

        // 如果随机时间 大于 100 ，那么触发容错
        int value = random.nextInt(200);

        System.out.println("helloWorld() costs " + value + " ms.");

        Thread.sleep(value);

        return "Hello,World";
    }

    public String errorContent() {
        return "Fault";
    }

}
