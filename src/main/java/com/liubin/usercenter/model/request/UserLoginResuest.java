package com.liubin.usercenter.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel("Json序列化 登陆 类")
public class UserLoginResuest implements Serializable {
    private static final long serialVersionUID = -3922046033886871205L;
    @ApiModelProperty("账号")
    private String userAccount;
    @ApiModelProperty("密码")
    private String userPassword;
}
