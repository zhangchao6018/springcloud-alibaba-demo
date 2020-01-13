package com.itmuch.contentcenter.auth;

import com.itmuch.contentcenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-13 08:18
 **/
@Aspect
@Component
public class CheckLoginAspect {
    @Autowired
    private JwtOperator jwtOperator;

    @Around("@annotation(com.itmuch.contentcenter.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point)  {
        try {
            //1.从header里面获取token
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
            HttpServletRequest request = attributes.getRequest();

            //2.校验token是否合法
            String token = request.getHeader("X-Token");
            //不合法或者已过期->false
            Boolean isValid = jwtOperator.validateToken(token);
            if (!isValid) {
                throw new SecurityException("token 不合法");
            }

            //3.如果校验成功过，就将用户信息
            Claims claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("id", claims.get("id"));
            request.setAttribute("wxNickname", claims.get("wxNickname"));
            request.setAttribute("role", claims.get("role"));

            return  point.proceed();
        } catch (Throwable throwable) {
            throw new SecurityException("token不合法");
        }
    }
}
