package com.itmuch.usercenter.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-09 23:42
 **/
@Slf4j
@Service
public class TestStreamConsumer {
    @StreamListener(Sink.INPUT)
    public void  receive(String messageBody){
        log.info("通过stream收到了消息messageBody= {}",messageBody);
    }

}
