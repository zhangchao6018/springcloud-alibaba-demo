package com.itmuch.contentcenter.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-04 11:21
 **/
@Service
@Slf4j
public class TestSentinelService {
    @SentinelResource("commmon")
    public String  common(){
        log.info("TestSentinelService被调用了...");
        return "yes";
    }
}
