package com.liubin.usercenter.service;
import java.util.Date;

import com.liubin.usercenter.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.swing.*;
import javax.xml.transform.Result;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 * @author  liubin
 */
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;
    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("liubin");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setPhone("xxxx");
        user.setGender(0);
        user.setUserPassword("568");
        user.setEmail("sdfvs");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);
        boolean save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(save);
    }

    @Test
    void userRegister() {
        String userAccount = "yupip";
        String userPassword="";
        String checkPassword = "123456789";
        String planetCode="1";
        long l = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,l);//密码为空
        userAccount = "yu";
        userPassword= "123456789";
        l = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,l);//用户账号小于4

        userAccount = "yup pa";
        userPassword="123456789";
        l = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,l);//有空格

        userAccount="123";
        userPassword="123456789";//重复用户
        l = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,l);

        userAccount = "yupi";
        userPassword="1234567897";//密码和校验密码 出错
        l = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,l);


        userAccount = "liubin6";
        userPassword="123456789";
        l = userService.userRegister(userAccount,userPassword, checkPassword,planetCode);
        Assertions.assertTrue(l>0);

    }
}