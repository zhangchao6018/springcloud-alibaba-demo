package com.itmuch.contentcenter.dao;

import com.itmuch.contentcenter.domain.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-05 22:43
 **/
public interface ShareMapper extends JpaRepository<Share,Integer> {
}
