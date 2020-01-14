package com.itmuch.usercenter.auth;

import com.itmuch.usercenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-13 08:18
 **/
@Aspect
@Component
public class AuthAspect {
    @Autowired
    private JwtOperator jwtOperator;

    @Around("@annotation(com.itmuch.usercenter.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {

        checkToken();
        return  point.proceed();

    }

    private void checkToken() {
        try {
            //1.从header里面获取token
            HttpServletRequest request = getHttpServletRequest();

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
        } catch (Throwable throwable) {
            throw new SecurityException("token不合法");
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
        return attributes.getRequest();
    }

    /**
     * 角色授权
     * @param point
     * @return
     */
    @Around("@annotation(com.itmuch.usercenter.auth.CheckAuthorization)")
    public Object CheckAuthorization(ProceedingJoinPoint point) throws Throwable {
        //1.验证token是否合法
        this.checkToken();

        try {
            //2. 验证角色是否匹配
            HttpServletRequest request = getHttpServletRequest();
            String role = (String) request.getAttribute("role");

            //对比注解中和request中的值

            MethodSignature signature = (MethodSignature) point.getSignature();
            // 拿到使用了该注解方法的定义
            Method method = signature.getMethod();
            CheckAuthorization annotation = method.getAnnotation(CheckAuthorization.class);
            String value = annotation.value();
            if(!role.equals(value)){
                throw new SecurityException("用户无权访问!");
            }

            //直接proceed 会导致任何报错,都会返回安全认证异常,这是有问题的,所以point.proceed();应该直接抛出去
//            return point.proceed();

        } catch (Throwable throwable) {
            throw new SecurityException("用户无权访问!",throwable);
        }
        return point.proceed();
    }
}
