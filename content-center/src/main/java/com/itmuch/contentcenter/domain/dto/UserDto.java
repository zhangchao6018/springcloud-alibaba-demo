package com.itmuch.contentcenter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-03 00:16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    private String wxId;

    private String wxNickname;

    private String roles;
}
