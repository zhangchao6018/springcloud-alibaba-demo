package com.itmuch.contentcenter.feignclient;

import com.itmuch.contentcenter.configuration.UsercenterFeignConfiguration;
import com.itmuch.contentcenter.domain.entity.User;
import com.itmuch.contentcenter.feignclient.fallbaackfactory.UsercenterFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-02 22:45
 **/
//@FeignClient(value = "user-center",configuration = UsercenterFeignConfiguration.class)
@FeignClient(value = "user-center"
        ,configuration = UsercenterFeignConfiguration.class
//        ,fallback = UsercenterFeignClientFallback.class //指定fallback类  此方法不能获取到异常 fallbackfactory可以    fallback和fallbackFactory只能用一种
        ,fallbackFactory = UsercenterFeignClientFallbackFactory.class
)
public interface  UserCenterFeignClient {

    @GetMapping("/users/{id}")
    User findById (@PathVariable Integer id);
}
