package com.itmuch.contentcenter.domain.dto;

import com.itmuch.contentcenter.enums.AuditStatusEnum;
import lombok.Data;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-05 22:40
 **/
@Data
public class ShareAuidtDto {
    /**
     * 审核状态描述
     */
    private AuditStatusEnum auditStatusEnum;

    private String reason;
}
