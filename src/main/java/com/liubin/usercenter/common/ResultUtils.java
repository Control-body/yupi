package com.liubin.usercenter.common;

/**
 * ClassName: Control.
 * Description: 帮助我们生成返回类
 * date: 2022/4/21 15:31
 *
 * @author Control.
 * @since JDK 1.8
 */
public class ResultUtils {
    public static<T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }

    public static<T> BaseResponse<T> error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }

    public static<T> BaseResponse<T> error(ErrorCode errorCode,String message,String description){
        return new BaseResponse(errorCode.getCode(),null,message,description);
    }


    public static<T> BaseResponse<T> error(int code,String message,String description){
        return new BaseResponse(code,null,message,description);
    }


}
