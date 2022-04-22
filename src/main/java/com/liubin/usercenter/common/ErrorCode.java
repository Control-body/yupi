package com.liubin.usercenter.common;

/**
 * ClassName: Control.
 * Description: 错误码
 * date: 2022/4/21 16:20
 *
 * @author Control.
 * @since JDK 1.8
 */
public enum ErrorCode {
    SUCCESS(0,"ok",""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求数为空",""),
    NO_AUTH(40101,"无权限",""),
    NO_LOGIN(40100,"未登录",""),
    SYSTEM_ERROR(50000,"系统内部异常","");
    /**
     *状态码信息
     */
    private final int code;
    /**
     * 状态码描述
     */
    private final String message;
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
