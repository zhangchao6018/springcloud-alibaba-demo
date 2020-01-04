package com.itmuch.contentcenter.feignclient;

import com.itmuch.contentcenter.configuration.UsercenterFeignConfiguration;
import com.itmuch.contentcenter.domain.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-02 22:45
 **/
//@FeignClient(value = "user-center",configuration = UsercenterFeignConfiguration.class)
@FeignClient(value = "user-center",configuration = UsercenterFeignConfiguration.class)
public interface  UserCenterFeignClient {

    @GetMapping("/users/{id}")
    User findById (@PathVariable Integer id);
}
