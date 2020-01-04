package com.itmuch.usercenter.dao.user;

import com.itmuch.usercenter.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhouli
 */
//@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
