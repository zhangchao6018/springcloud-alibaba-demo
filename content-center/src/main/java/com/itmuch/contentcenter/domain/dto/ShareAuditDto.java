package com.itmuch.contentcenter.domain.dto;

import com.itmuch.contentcenter.enums.AuditStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-05 22:40
 **/
@Data
public class ShareAuditDto implements Serializable {

    private static final long serialVersionUID = 8122338190505722698L;
    /**
     * 审核状态描述
     */
    private AuditStatusEnum auditStatusEnum;

    private String reason;
}
