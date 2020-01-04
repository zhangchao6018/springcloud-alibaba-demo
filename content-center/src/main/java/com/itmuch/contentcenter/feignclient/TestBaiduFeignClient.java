package com.itmuch.contentcenter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-03 00:32
 **/
@FeignClient(value = "baidu",url = "http://www.baidu.com")
public interface TestBaiduFeignClient {

    @GetMapping("")
    String index();

}
