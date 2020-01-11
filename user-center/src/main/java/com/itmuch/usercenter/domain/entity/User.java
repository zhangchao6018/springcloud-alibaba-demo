package com.itmuch.usercenter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhouli
 * org.hibernate.LazyInitializationException: could not initialize proxy [com.itmuch.usercenter.domain.entity.User#1] - no Session
 * 	at org.hibernate.proxy.AbstractLazyInitializer.initialize(AbstractLazyInitializer.java:169) ~[hibernate-core-5.3.7.Final.jar:5.3.7.Final]
 * 	at org.hibernate.proxy.AbstractLazyInitializer.getImplementation(AbstractLazyInitializer.java:309) ~[hibernate-core-5.3.7.Final.jar:5.3.7.Final]
 * 	at org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor.intercept(ByteBuddyInterceptor.java:45) ~[hibernate-core-5.3.7.Final.jar:5.3.7.Final]
 * 	at org.hibernate.proxy.ProxyConfiguration$InterceptorDispatcher.intercept(ProxyConfiguration.java:95) ~[hibernate-core-5.3.7.Final.jar:5.3.7.Final]
 * 	-> @Proxy(lazy = false)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Proxy(lazy = false)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column
  private String wxId;
  @Column
  private String wxNickname;
  @Column
  private String roles;
  @Column
  private String avatarUrl;
  @Column
  private Date createTime;
  @Column
  private Date updateTime;
  @Column
  private Integer bonus;
}
