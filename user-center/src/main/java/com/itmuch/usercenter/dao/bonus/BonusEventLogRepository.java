package com.itmuch.usercenter.dao.bonus;

import com.itmuch.usercenter.domain.entity.BonusEventLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-07 23:25
 **/
public interface BonusEventLogRepository extends JpaRepository<BonusEventLog, Integer> {
}
