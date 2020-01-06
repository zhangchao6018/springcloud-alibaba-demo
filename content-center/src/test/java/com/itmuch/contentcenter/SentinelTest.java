package com.itmuch.contentcenter;

import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-04 11:09
 **/
public class SentinelTest {
    public static void main(String[] args) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        for (int j=1;j<=1000 ;j++)
        {
            String forObject = restTemplate.getForObject("http://localhost:8011/test/test2", String.class);
            Thread.sleep(500);
        }
    }
}
