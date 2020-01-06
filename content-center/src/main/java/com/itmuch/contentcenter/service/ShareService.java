package com.itmuch.contentcenter.service;

import com.itmuch.contentcenter.dao.ShareMapper;
import com.itmuch.contentcenter.domain.dto.ShareAuidtDto;
import com.itmuch.contentcenter.domain.dto.UserDto;
import com.itmuch.contentcenter.domain.entity.Share;
import com.itmuch.contentcenter.domain.entity.User;
import com.itmuch.contentcenter.feignclient.TestUserCenterFeignClient;
import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

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

    @Autowired
    ShareMapper shareMapper;

    public User findById(Integer userId) {
//        String forObject = restTemplate.getForObject("http://user-center/users/{id}", String.class, userId);
        User user = userCenterFeignClient.findById(userId);
        return user;
    }

    public User findByCondition(UserDto userDto) {
        User user = testUserCenterFeignClient.testQuery(userDto);
        return user;
    }


    public Share auditbyId(Integer id, ShareAuidtDto auidtDto) {
        Share one = shareMapper.getOne(id);

        //1.查询share是否存在
        if (one==null){
            throw new IllegalArgumentException("参数非法，改分享不存在");
        }
        // 2.审核资源 将状态设置为 PASS/REJECT
        if (!Objects.equals("NOT_YET",one.getAuditStatus())){
            throw new IllegalArgumentException("参数非法，改分享已审核通过或者审核不通过");
        }
        one.setAuditStatus(auidtDto.getAuditStatusEnum().toString());
        this.shareMapper.save(one);
        //3. 如果是PASSname将发布人添加积分
        // 异步执行

        return one;

    }
}
