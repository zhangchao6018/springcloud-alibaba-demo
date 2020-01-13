package com.itmuch.usercenter.controller.user;

import com.itmuch.usercenter.auth.CheckLogin;
import com.itmuch.usercenter.dao.user.UserRepository;
import com.itmuch.usercenter.domain.dto.user.JwtTokenrespDto;
import com.itmuch.usercenter.domain.dto.user.LoginRespDto;
import com.itmuch.usercenter.domain.dto.user.UserLoginDto;
import com.itmuch.usercenter.domain.dto.user.UserRespDto;
import com.itmuch.usercenter.domain.entity.User;
import com.itmuch.usercenter.util.JwtOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhouli
 */
@Slf4j
@RequestMapping("/users")
@RestController
@Api(value = "用户接口文档", description = "用户接口文档", tags = {"user-Document"})
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtOperator jwtOperator;

  @CheckLogin
  @GetMapping("/{id}")
  public Optional<User> findById(@PathVariable Integer id) {
    log.info("我被请求了...");
    Optional<User> byId = userRepository.findById(id);
    System.out.println(byId);
    return Optional.of(byId.get());
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


  /**
   * 模拟多条件查询  传一个user返回对应user
   * @param
   * @return
   */
  @ApiOperation(value="[登录]接口 ",notes="[登录]接口 ")
  @GetMapping("/login")
  public LoginRespDto login(@RequestBody UserLoginDto dto) {
    //这里忽视业务  不作任何操作 直接返回token

    User user = new User();
    BeanUtils.copyProperties(dto, user);
    user.setId(1234);
    user.setBonus(200);
    user.setAvatarUrl("45454545.img");
    user.setRoles("admin");

    //颁发token
    Map<String , Object> userInfo = new HashMap<>(3);
    userInfo.put("id", user.getId());
    userInfo.put("wxNickname", user.getWxNickname());
    userInfo.put("role", "admin");

    String token = jwtOperator.generateToken(userInfo);
    log.info("用户{}登录成功，生成token={},有效期到：{}",
            dto.getWxNickname(),
            token,
            jwtOperator.getExpirationDateFromToken(token)
            );

    //构建相应

    return  LoginRespDto.builder()
            .user(
                    UserRespDto.builder()
                            .id(user.getId())
                            .avatarUrl(user.getAvatarUrl())
                            .bonus(user.getBonus())
                            .wxNickname(user.getWxNickname())
                            .build()
            )
            .token(
                    JwtTokenrespDto.builder()
                            .expirationTime( jwtOperator.getExpirationDateFromToken(token).getTime())
                            .token(token)
                            .build()
            )
            .build()
            ;
  }
}
