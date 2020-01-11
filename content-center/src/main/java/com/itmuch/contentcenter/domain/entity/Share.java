package com.itmuch.contentcenter.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * 记录一个报错由于使用lombok插件，导致序列化问题  -》@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
 * com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.itmuch.contentcenter.domain.entity.Share$HibernateProxy$hE4OmP5R["hibernateLazyInitializer"])
 * @Author: zhangchao
 * @Date: 2020-01-05 22:39
 **/
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Accessors(chain = true)
public class Share implements Serializable {
    private static final long serialVersionUID = 3014886695267042658L;
    @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
