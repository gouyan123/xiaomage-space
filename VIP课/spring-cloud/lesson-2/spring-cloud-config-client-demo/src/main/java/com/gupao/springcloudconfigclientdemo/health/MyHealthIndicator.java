package com.gupao.springcloudconfigclientdemo.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
 * TODO:小马哥，写点注释吧！
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-25
 **/
public class MyHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder)
            throws Exception {
        builder.down().withDetail("MyHealthIndicator","Down");
    }
}
