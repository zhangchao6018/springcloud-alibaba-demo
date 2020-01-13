package com.itmuch.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-12 23:20
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRespDto {

    //token
    private JwtTokenrespDto token;

    //用户信息
    private UserRespDto user;

}
