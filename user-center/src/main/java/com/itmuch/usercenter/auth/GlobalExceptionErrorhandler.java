package com.itmuch.usercenter.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-13 08:45
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionErrorhandler {
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorBody> error(SecurityException e){
        log.warn("发生securityException 异常",e);
       return new ResponseEntity<ErrorBody>(
                ErrorBody.builder()
                        .body("Token非法")
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .build(),
               HttpStatus.UNAUTHORIZED
        );

    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ErrorBody {
    private String body;
    private int status;
}