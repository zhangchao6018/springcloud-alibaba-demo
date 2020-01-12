package com.itmuch.contentcenter.service;

import com.alibaba.fastjson.JSON;
import com.itmuch.contentcenter.dao.RocketmqTransactionLogMapper;
import com.itmuch.contentcenter.dao.ShareMapper;
import com.itmuch.contentcenter.domain.dto.ShareAuditDto;
import com.itmuch.contentcenter.domain.dto.UserDto;
import com.itmuch.contentcenter.domain.dto.message.UserAddBonusMsgDto;
import com.itmuch.contentcenter.domain.entity.RocketmqTransactionLog;
import com.itmuch.contentcenter.domain.entity.Share;
import com.itmuch.contentcenter.domain.entity.User;
import com.itmuch.contentcenter.enums.AuditStatusEnum;
import com.itmuch.contentcenter.feignclient.TestUserCenterFeignClient;
import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-01 18:39
 **/
@Slf4j
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

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Autowired
    RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

    @Autowired
    Source source;


    public User findById(Integer userId) {
//        String forObject = restTemplate.getForObject("http://user-center/users/{id}", String.class, userId);
        User user = userCenterFeignClient.findById(userId);
        return user;
    }

    public User findByCondition(UserDto userDto) {
        User user = testUserCenterFeignClient.testQuery(userDto);
        return user;
    }


    public Share auditbyId(Integer id, ShareAuditDto auditDto) {
        Share share = shareMapper.getOne(id);

        //1.查询share是否存在
        if (share==null){
            throw new IllegalArgumentException("参数非法，改分享不存在");
        }
        if (!Objects.equals("NOT_YET",share.getAuditStatus())){
            throw new IllegalArgumentException("参数非法，改分享已审核通过或者审核不通过");
        }

        // 2. 如果是pass，name将发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        if(AuditStatusEnum.PASS.equals(auditDto.getAuditStatusEnum())){
            //spring-stream编程模型重构
            String  transactionId = UUID.randomUUID().toString();
            this.source.output()
                    .send(MessageBuilder.withPayload(
                            UserAddBonusMsgDto.builder()
                                    .userId(share.getUserId())
                                    .bonus(50)
                                    .build())
                            .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                            .setHeader("share_id",id)
                            .setHeader("dto", JSON.toJSONString(auditDto))
                            .build());

            //rocketmq 原生方式发送半消息
//            prepareMessage(id, auditDto, share);

            // 发送普通消息,并入库（demo）
//            nomalMessage(id, auditDto, share);
            log.info("消息发送成功。。。。");

        }else {
            //如果不是审核通过的操作 只需更新数据库（只有审核通过需要加积分）
            this.auditbyId(id, auditDto);
        }
        return share;

    }

    /**
     * 发送半消息 rocketmq 原生方式
     * @param id
     * @param auditDto
     * @param share
     */
    private void prepareMessage(Integer id, ShareAuditDto auditDto, Share share) {
        String  transactionId = UUID.randomUUID().toString();
        log.info("transactionId={}",transactionId);
        // 发送半消息
        rocketMQTemplate.sendMessageInTransaction(
                "wx-add-bonus-group",
                "add-bonus",
                MessageBuilder.withPayload(
                        UserAddBonusMsgDto.builder()
                                .userId(share.getUserId())
                                .bonus(50)
                                .build())
                        .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                        .setHeader("share_id",id)
                        .build(),
                // arg 有大用处
                auditDto
        );
    }

    private void nomalMessage(Integer id, ShareAuditDto auditDto, Share share) {
        rocketMQTemplate.convertAndSend(
                "add-bonus",
                UserAddBonusMsgDto.builder()
                .userId(share.getUserId())
                .bonus(50)
                .build()
        );
        auditByIdInDB(id,auditDto);
    }

    // 2.审核资源 将状态设置为 PASS/REJECT
    @Transactional(rollbackFor = Exception.class)
    public void auditByIdInDB(Integer id , ShareAuditDto auditDto) {
        //先查询再刷新
        Share result = shareMapper.getOne(id);
        result.setAuditStatus(auditDto.getAuditStatusEnum().toString())
                .setReason(auditDto.getReason());

        this.shareMapper.save(result);
        // 4. 把share 写到缓存
    }

    /***
     *
     * @param id
     * @param auditDto
     * @param transactionId
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditByIdWithRocketMqLog(Integer id , ShareAuditDto auditDto,String transactionId) {
        this.auditByIdInDB(id,auditDto);
        this.rocketmqTransactionLogMapper.save(
                RocketmqTransactionLog.builder()
                .transactionId(transactionId)
                .log("审核分享")
                .build()
        );

    }
}
