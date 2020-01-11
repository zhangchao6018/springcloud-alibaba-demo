package com.itmuch.contentcenter.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-06 23:37
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddBonusMsgDto implements Serializable {

    private static final long serialVersionUID = 1328136513336811027L;
    private Integer userId;

    private Integer bonus;
}
