package com.itmuch.usercenter.controller.user;

import com.itmuch.usercenter.dao.user.UserRepository;
import com.itmuch.usercenter.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author zhouli
 */
@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/{id}")
  public Optional<User> findById(@PathVariable Long id) {
    log.info("我被请求了...");
    Optional<User> byId = userRepository.findById(id);
    System.out.println(byId);
    User user = new User(1l, "超超", "超", "本人");
    return Optional.of(user);
    //return this.userRepository.findById(id);
  }

  /**
   * 模拟多条件查询  传一个user返回对应user
   * @param user
   * @return
   */
  @GetMapping("/query")
  public User findByCondition(User user) {
    return user;
  }
}
