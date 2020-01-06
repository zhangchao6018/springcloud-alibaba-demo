package com.itmuch.contentcenter.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.itmuch.contentcenter.domain.dto.UserDto;
import com.itmuch.contentcenter.domain.entity.User;
import com.itmuch.contentcenter.feignclient.TestBaiduFeignClient;
import com.itmuch.contentcenter.service.ShareService;
import com.itmuch.contentcenter.service.TestSentinelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    private TestSentinelService testSentinelService;

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


    //测试 根据链路入口名称：@GetMapping("/pointa")  访问@SentinelResource("commmon") 资源 ->限流
    @GetMapping("/pointa")
    public String pointA(){
        testSentinelService.common();
        return "pointA";
    }

    @GetMapping("/pointb ")
    public String pointB(){
        testSentinelService.common();
        return "pointB";
    }


    //热点资源
    @GetMapping("/hot")
    @SentinelResource("hot")
    public String hot(@RequestParam(required = false) String a ,
                      @RequestParam(required = false) String b){
        return a+" "+b;
    }


    //服务源  自定义保护资源
    @GetMapping("/test-sentinel-api")
    public String testSentinel(@RequestParam(required = false) String a){
        String resourcename = "test-sentinel-api";

        //模拟是某个微服务来源调用的
        ContextUtil.enter(resourcename,"test-weifuwu");

        // 定义一个被sentinel保护的资源
        Entry entry = null;

        try {
            entry = SphU.entry(resourcename);
            if (StringUtils.isEmpty(a)){
                throw new IllegalArgumentException("a参数不能为空");
            }

            return a;
        }
        //被保护的资源被限流或者降级了，就会抛这个异常
        catch (BlockException e) {
            log.warn("限流了或者降级了", e);
         return "限流了或者降级了";
        }
        catch (IllegalArgumentException e2) {
            //统计异常发生次数  发生占比等
            Tracer.trace(e2);
            return "参数非法";
        }

        finally {
            if (entry!=null){
                // 退出 entry
                 entry.exit();
            }
            ContextUtil.exit();
        }

    }


    //@SentinelResource重构
    @GetMapping("/test-sentinel-resource")
    @SentinelResource(value = "test-sentinel-api",blockHandler = "block",fallback = "fallback")
    public String testSentinel2(@RequestParam(required = false) String a){

            if (StringUtils.isEmpty(a)){
                throw new IllegalArgumentException("a参数不能为空");
            }

            return a;

    }

    //@SentinelResource会自动调用，并且自动 Tracer.trace(e2)
    //处理限流或者降级
    public String block( String a, BlockException e){
        log.warn("限流了或者降级了", e);
        return "限流了或者降级了 block";
    }

    //处理降级
    //Sentinel 1.6 可以处理throwable
    public String fallback( String a){
        return "限流了或者降级了  fallback";
    }
}
