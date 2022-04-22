package com.liubin.usercenter.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
@ApiModel("用户实体类（MySQL）")
public class User implements Serializable {
    /**
     * 
     */
    @ApiModelProperty("用户 id")
    private Long id;

    /**
     * 用户名称
     */
    @ApiModelProperty("用户 name")
    private String username;

    /**
     * 账号
     */
    @ApiModelProperty("用户 Account")
    private String userAccount;

    /**
     * 头像
     */
    @ApiModelProperty("用户 头像")
    private String avatarUrl;

    /**
     * 电话
     */
    @ApiModelProperty("用户 电话")
    private String phone;

    /**
     * 性别
     */
    @ApiModelProperty("用户 性别")
    private Integer gender;

    /**
     * 密码
     */
    @ApiModelProperty("用户 密码")
    private String userPassword;

    /**
     * 电子邮箱
     */
    @ApiModelProperty("用户 电子邮箱")
    private String email;

    /**
     * 0 正常
     */
    @ApiModelProperty("用户 状态")
    private Integer userStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 0 1
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 用户角色 0 管理员 1 用户
     */
    private Integer userRole;

    /**
     * 星球编号
     */
    private String planetCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}