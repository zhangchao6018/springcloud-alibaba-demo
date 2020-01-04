package com.itmuch.contentcenter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author zhouli
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  private String wxId;
  @Column
  private String wxNickname;
  @Column
  private String roles;
}
