package com.itmuch.contentcenter.configuration;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description: 相同集群优先策略（容灾，如地区停电等）
 * 扩展：
 * 扩展Ribbon支持基于元数据的版本管理
 * https://www.imooc.com/article/288674
 *
 * @Author: zhangchao
 * @Date: 2020-01-02 00:07
 **/
@Slf4j
public class NacosSameClusterWeightedRule extends AbstractLoadBalancerRule {
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        try {
            //拿到配置文件的集群名称 BeiJing
            String clusterName = nacosDiscoveryProperties.getClusterName();

            // ribbon 入口
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            //想要请求的微服务的名称
            String name = loadBalancer.getName();

            //实现负载均衡算法  这里用nacos写好的
            //拿到服务发现的相关api
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();


            //1.找到指定服务所有实例 A  只拿健康的实例
            List<Instance> instances = namingService.selectInstances(name, true);

            //2.过滤出相同集群下的所有实例 B
            List<Instance> sameClusterinstances = instances.stream()
                    .filter(instance -> Objects.equals(instance.getClusterName(), clusterName))
                    .collect(Collectors.toList());

            //3.如果B是空，就用A
            List<Instance> instancesTobeChosen = new ArrayList<>();
            if (CollectionUtils.isEmpty(sameClusterinstances)){
                instancesTobeChosen=instances;
                //说明发生了跨集群调用
                log.warn( "发生跨集群的调用，name = {} , clusterName={}, instances={}",
                        name,clusterName,instances
                );
            }else {
                instancesTobeChosen = sameClusterinstances;
            }

            //4.基于权重的负载均衡算法，返回一个实例
            Instance instance = Extendbalancer.getHostByRandomWeight2(instancesTobeChosen);
            log.info("选择的实例是 port = {} ,instance= {}",instance.getPort(),instance);
            return new NacosServer(instance);

        } catch (NacosException e) {
            log.error("发生异常了。。。",e);
            e.printStackTrace();
            return null;
        }

    }
}
//todo 当想利用别人写好代码 但是别人的是protected，可以选择继承，简单包装下，再调用
class Extendbalancer extends Balancer{
    public static Instance getHostByRandomWeight2(List<Instance> hosts) {
        //调用父类...
        return getHostByRandomWeight(hosts);
    }

}
