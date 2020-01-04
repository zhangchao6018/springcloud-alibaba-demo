package com.itmuch.contentcenter.feignclient;

import com.itmuch.contentcenter.domain.dto.UserDto;
import com.itmuch.contentcenter.domain.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * 如何使用Feign构造多参数的请求
 *      https://www.imooc.com/article/289000
 * @Author: zhangchao
 * @Date: 2020-01-03 00:17
 **/
@FeignClient("user-center")
public interface TestUserCenterFeignClient {
    //@SpringQueryMap  解决feign.FeignException: status 405 reading TestUserCenterFeignClient#testQuery(UserDto)
    //算是feign小缺陷
    @GetMapping("/users/query")
    User testQuery(@SpringQueryMap UserDto userDto);
}
