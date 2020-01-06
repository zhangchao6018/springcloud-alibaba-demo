package com.itmuch.contentcenter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-05 22:39
 **/
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Share {
    @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer userId;
    @Column
    private String title;
    @Column
    private Date createTime;
    @Column
    private Date updateTime;
    @Column
    private Integer isOriginal;
    @Column
    private String author;
    @Column
    private String cover;
    @Column
    private String summary;
    @Column
    private Integer price;
    @Column
    private String downloadUrl;
    @Column
    private String buyCount;
    @Column
    private Integer showFlag;
    @Column
    private String auditStatus;
    @Column
    private String reason;

}
