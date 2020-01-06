package com.itmuch.contentcenter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-05 22:46
 **/
@Getter@AllArgsConstructor
public enum  AuditStatusEnum {
    /**
     * 待审核 审核通过 审核不通过
     */
    NOT_YET,
    PASS,
    REJECT
}
