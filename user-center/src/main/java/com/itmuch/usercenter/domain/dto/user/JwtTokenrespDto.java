package com.itmuch.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-12 23:07
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenrespDto {
    private String token;

    /**
     * 过期时间
     */
    private Long expirationTime;
}
