package com.itmuch.contentcenter.controller;

import com.itmuch.contentcenter.domain.dto.UserDto;
import com.itmuch.contentcenter.domain.entity.User;
import com.itmuch.contentcenter.feignclient.TestBaiduFeignClient;
import com.itmuch.contentcenter.service.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-01 18:37
 **/
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ShareService shareService;

    @Autowired
    private TestBaiduFeignClient testBaiduFeignClient;

    @GetMapping("/test1/{userId}")
    public User test(@PathVariable Integer userId ){
        //log.info("我被请求了....");
        return shareService.findById(userId);
    }

    //测试多参数feign调用
    @GetMapping("/test2")
    public User test(UserDto userDto){
        return shareService.findByCondition(userDto);
    }

    //测试feign调用没有注册到nacos的服务
    @GetMapping("/baidu")
    public String testBaidu(UserDto userDto){
        return testBaiduFeignClient.index();
    }
}
