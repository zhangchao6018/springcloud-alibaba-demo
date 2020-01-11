package com.itmuch.contentcenter.rocketmq;

import com.itmuch.contentcenter.dao.RocketmqTransactionLogMapper;
import com.itmuch.contentcenter.domain.dto.ShareAuditDto;
import com.itmuch.contentcenter.domain.entity.RocketmqTransactionLog;
import com.itmuch.contentcenter.service.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Optional;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-07 22:37
 **/
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "wx-add-bonus-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {

    private final ShareService shareService;
    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

    /**
     *执行本地事务
     * @param message
     * @param args
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object args) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer shareId = Integer.valueOf((String) headers.get("share_id"));

        try {
            log.info("执行审核信息保存(本地事务)....");
            this.shareService.auditByIdWithRocketMqLog( shareId, (ShareAuditDto)args,transactionId);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.info("保存审核信息异常....");
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    //本地事务的检查方法
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);

        RocketmqTransactionLog rocketmqTransactionLog = new RocketmqTransactionLog();
        rocketmqTransactionLog.setTransactionId(transactionId);

        Example<RocketmqTransactionLog> example =  Example.of(rocketmqTransactionLog);

        Optional<RocketmqTransactionLog> one = this.rocketmqTransactionLogMapper.findOne(example);
        RocketmqTransactionLog rocketmqTransactionLogDB = one.get();

        if (rocketmqTransactionLogDB !=null){
            log.info("本地事务提交....");
            return RocketMQLocalTransactionState.COMMIT;
        }
        log.warn("本地事务回滚了....");
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
