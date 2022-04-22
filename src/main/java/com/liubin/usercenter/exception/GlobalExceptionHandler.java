package com.liubin.usercenter.exception;

import com.liubin.usercenter.common.BaseResponse;
import com.liubin.usercenter.common.ErrorCode;
import com.liubin.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName: Control.
 * Description:
 * date: 2022/4/21 18:41
 *
 * @author Control.
 * @since JDK 1.8
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
      log.error("businessException",e.getMessage(),e);
      return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }


    @ExceptionHandler(RuntimeException.class)
    public  BaseResponse runtimeExceptionHandler(RuntimeException e){
        log.error("runtimeException",e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }
}
