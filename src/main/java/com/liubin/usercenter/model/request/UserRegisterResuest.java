package com.liubin.usercenter.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.javassist.SerialVersionUID;

import java.io.Serializable;

/**
 * @author liubin
 */
@Data
@ApiModel("Json序列化 注册类")
public class UserRegisterResuest implements Serializable {
     private static final long serialVersionUID = -3922046033886871205L;
     @ApiModelProperty("账号")
     private String userAccount;
     @ApiModelProperty("密码")
     private String userPassword;
     @ApiModelProperty("确认密码")
     private String checkPassword;
     @ApiModelProperty("型球编号")
     private String planetCode;
}
