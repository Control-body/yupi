package com.liubin.usercenter.service;

import com.liubin.usercenter.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author luer
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2022-03-18 21:14:12
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册： javadoc 注释
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @param planetCode  星球编号
     * @return
     */
    long userRegister(String userAccount,String userPassword,String checkPassword,String planetCode);

    /**
     *
     * @param userAccount
     * @param userPassword
     * @return
     */
    User dologin(String userAccount,String userPassword,HttpServletRequest request);

    List<User> searchUsers(String username);

    User getSafetyUser(User user);

    int userLogout(HttpServletRequest request);
}
