package com.itmuch.contentcenter.configuration;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;

/**
 * @Description: ribbon不支持 nacos 自定义调用权重，需要手动写
 * @Author: zhangchao
 * @Date: 2020-01-01 23:28
 **/
@Slf4j
public class NacosWeightedRule extends AbstractLoadBalancerRule {
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        //读取配置文件并初始化
    }

    @Override
    public Server choose(Object o) {
        try {
            // ribbon 入口
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            //log.info("lb={}",loadBalancer);
            //想要请求的微服务的名称
            String name = loadBalancer.getName();

            //实现负载均衡算法  这里用nacos写好的
            //拿到服务发现的相关api
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

            // nacos  client 自动通过局域权重的负载均衡算法，给我们选择一个实例

            Instance instance = namingService.selectOneHealthyInstance(name);

            log.info("选择的实例是：port = {},instance = {}",instance.getPort(),instance);
            return new NacosServer(instance);
        } catch (NacosException e) {
            e.printStackTrace();
            return null;
        }

    }
}
