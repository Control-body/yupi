package com.liubin.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: Control.
 * Description:  通用返回类
 * date: 2022/4/21 15:22
 *
 * @author Control.
 * @since JDK 1.8
 */
@Data
public class BaseResponse<T> implements Serializable {
    private  int code;
    // 加入泛型 提高代码的 可通用性数据 可能包含很多种类型
    private  T data;
    private  String message;
    private String  description;
    public BaseResponse(int code, T data, String message,String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description=description;
    }
    public BaseResponse(int code, T data,String message) {
        this(code,data,message,"");
    }
    public BaseResponse(int code, T data) {
        this(code,data,"","");
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }
}
