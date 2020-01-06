package com.itmuch.contentcenter.sentineltest;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 根据不同请求调用方来源配置
 * @Author: zhangchao
 * @Date: 2020-01-05 21:02
 **/
//@Component
public class MyRequestoriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        //从请求参数中获取名为 origin 的参数
        // 如果获取不到 origin参数 就抛异常

        String origin = request.getParameter("origin");
        if (StringUtils.isEmpty(origin)){
            throw new IllegalArgumentException("origin must be specified...");
        }
        return origin;
    }
}
