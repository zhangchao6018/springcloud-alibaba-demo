package com.itmuch.usercenter.domain.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-12 23:27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDto {

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "avatarUrl")
    private String avatarUrl;

    @ApiModelProperty(value = "wxNickname")
    private String wxNickname;
}
