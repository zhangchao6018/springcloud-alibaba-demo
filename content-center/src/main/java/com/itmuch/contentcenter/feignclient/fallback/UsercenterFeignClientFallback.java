package com.itmuch.contentcenter.feignclient.fallback;

import com.itmuch.contentcenter.domain.entity.User;
import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-04 18:49
 **/
@Component
public class UsercenterFeignClientFallback implements UserCenterFeignClient {

    @Override
    public User findById(Integer id) {
        User user = new User();
        user.setWxNickname("一个默认用户");
        return user;
    }
}
