package com.itmuch.contentcenter.dao;

import com.itmuch.contentcenter.domain.entity.RocketmqTransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-07 22:53
 **/

public interface RocketmqTransactionLogMapper extends JpaRepository<RocketmqTransactionLog,Integer> {
}
