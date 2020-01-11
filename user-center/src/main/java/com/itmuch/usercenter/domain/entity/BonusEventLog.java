package com.itmuch.usercenter.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-07 23:26
 **/
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class BonusEventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer userId;

    @Column
    private Integer value;

    @Column
    private String event;

    @Column
    private Date createTime;

    @Column
    private String description;

}
