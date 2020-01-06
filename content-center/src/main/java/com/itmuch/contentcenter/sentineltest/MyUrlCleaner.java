package com.itmuch.contentcenter.sentineltest;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Description:
 * sentinel不支持占位符 所以需要配置url资源名通配
 * 让 http://localhost:8011/test/test1/1   http://localhost:8011/test/test1/2 等 有相同的限流规则  ->让其有相同的资源名称
 * @Author: zhangchao
 * @Date: 2020-01-05 21:41
 **/
@Slf4j
@Component
public class MyUrlCleaner implements UrlCleaner {
    @Override
    public String clean(String originUrl) {
        log.info(originUrl);
        //让/test/test1/1  /test/test1/2的返回值相同
        String[] split = originUrl.split("/");
        return Arrays.stream(split)
                .map(string ->{
                    if (NumberUtils.isNumber(string)){
                        return "{number}";
                    }
                    return string;
                })
                .reduce((a,b)->a+"/"+b)
                .orElse("");
    }
}
