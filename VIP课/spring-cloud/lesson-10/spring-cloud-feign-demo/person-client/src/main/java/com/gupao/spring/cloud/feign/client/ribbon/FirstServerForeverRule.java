package com.gupao.spring.cloud.feign.client.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * 自定义实现 {@link IRule}
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/5
 */
public class FirstServerForeverRule extends AbstractLoadBalancerRule {


    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {

        ILoadBalancer loadBalancer = getLoadBalancer();

        // 返回三个配置 Server，即：
        // person-service.ribbon.listOfServers = \
        // http://localhost:9090,http://localhost:9090,http://localhost:9090
        List<Server> allServers = loadBalancer.getAllServers();

        return allServers.get(0);
    }
}
