package com.itmuch.contentcenter.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @Description:
 * feign 的配置类 别家@configuraton注解，否则必须加在@component 扫描的包之外，不然该配置会被全局公用
 * @Author: zhangchao
 * @Date: 2020-01-02 23:13
 **/
public class UsercenterFeignConfiguration {
    @Bean
    public Logger.Level level(){
        //打印所有请求细节
        return Logger.Level.FULL;
    }
}
