package com.itmuch.contentcenter.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import ribbonconfiiguration.RibbonConfiguration;


/**
 * @Description:
 *  细粒度配置指定服务提供者的负载均衡调用策略 -》父子上下文的坑（重复加载问题-》类比controller component相互排除）
 * @Author: zhangchao
 * @Date: 2020-01-01 22:39
 **/
//@RibbonClient(name = "user-center",configuration = RibbonConfiguration.class)  //细粒度ribbon配置方式
//@Configuration
@RibbonClients(defaultConfiguration =  RibbonConfiguration.class)  //全局ribbon配置方式
@Configuration
public class UserCenterRibbonConfiguration {
}
