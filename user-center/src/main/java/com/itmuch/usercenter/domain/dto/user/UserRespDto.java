package com.itmuch.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-12 23:19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRespDto {
    private Integer id;

    private String avatarUrl;

    private Integer bonus;

    private String wxNickname;
}
