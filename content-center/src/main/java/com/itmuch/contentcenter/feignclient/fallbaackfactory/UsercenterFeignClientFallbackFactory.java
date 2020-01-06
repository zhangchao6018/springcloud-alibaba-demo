package com.itmuch.contentcenter.feignclient.fallbaackfactory;

import com.itmuch.contentcenter.domain.entity.User;
import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-04 18:55
 **/
@Slf4j
@Component
public class UsercenterFeignClientFallbackFactory implements FallbackFactory<UserCenterFeignClient> {

    @Override
    public UserCenterFeignClient create(Throwable throwable) {
        return new UserCenterFeignClient() {
            @Override
            public User findById(Integer id) {
                log.warn("远程调用被限流/降级", throwable);
                User user = new User();
                user.setWxNickname("一个默认用户");
                return user;
            }
        };
    }
}
