package com.itmuch.contentcenter.service;

import com.itmuch.contentcenter.domain.dto.UserDto;
import com.itmuch.contentcenter.domain.entity.User;
import com.itmuch.contentcenter.feignclient.TestUserCenterFeignClient;
import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-01 18:39
 **/
@Service
public class ShareService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserCenterFeignClient userCenterFeignClient;

    @Autowired
    TestUserCenterFeignClient testUserCenterFeignClient;

    public User findById(Integer userId) {
//        String forObject = restTemplate.getForObject("http://user-center/users/{id}", String.class, userId);
        User user = userCenterFeignClient.findById(userId);
        return user;
    }

    public User findByCondition(UserDto userDto) {
        User user = testUserCenterFeignClient.testQuery(userDto);
        return user;
    }
}
