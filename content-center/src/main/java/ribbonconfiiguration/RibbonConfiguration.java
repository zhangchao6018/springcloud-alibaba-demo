package ribbonconfiiguration;

import com.itmuch.contentcenter.configuration.NacosSameClusterWeightedRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 *  设置策略为随机（放启动类路径会造成所有的provider都用该配置，因此比如说content服务根据调用的提供者需要配置5种策略，需要在启动类包外创建对应策略，然后再在启动类包内根据具体的提供者配置）
 * @Author: zhangchao
 * @Date: 2020-01-01 22:40
 **/
@Configuration
public class RibbonConfiguration {
    @Bean
    public IRule ribbonIRule(){
//        return new RandomRule();
        // 测试自定义权重负载均衡策略
//        return new NacosWeightedRule();
        // 优先相同集群策略
        return new NacosSameClusterWeightedRule();
    }
}
