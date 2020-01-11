package com.itmuch.usercenter.rocketmq;

import com.itmuch.usercenter.dao.bonus.BonusEventLogRepository;
import com.itmuch.usercenter.dao.user.UserRepository;
import com.itmuch.usercenter.domain.dto.message.UserAddBonusMsgDto;
import com.itmuch.usercenter.domain.entity.BonusEventLog;
import com.itmuch.usercenter.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-07 23:17
 **/
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "consumer-group",topic = "add-bonus")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusListner implements RocketMQListener<UserAddBonusMsgDto> {

    private final UserRepository userRepository;

    private final BonusEventLogRepository bonusEventLogRepository;

    //当收到消息的时候执行的业务
    @Override
    public void onMessage(UserAddBonusMsgDto userAddBonusMsgDto) {
        //1. 为用户加积分
        Integer userId = userAddBonusMsgDto.getUserId();
        User user = userRepository.getOne(userId);

        Integer bonus = userAddBonusMsgDto.getBonus();

        if (user==null){
            log.error("不存在该用户...");
            return;
        }
        //作非空校验..再加积分
        user.setBonus(user.getBonus()+ bonus);
        userRepository.save(user);

        //2.记录日志到bonus_event_log表
        bonusEventLogRepository.save(
                BonusEventLog.builder()
                        .userId(userId)
                        .value(bonus)
                        .event("CONTRIBUTE")
                        .createTime(new Date())
                        .description("投稿加积分")
                        .build()
        );
        log.info("积分添加完毕...");
    }
}
