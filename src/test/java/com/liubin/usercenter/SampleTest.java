package com.liubin.usercenter;


import com.liubin.usercenter.mapper.UserMapper;
import com.liubin.usercenter.model.User;
import org.junit.Assert;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class SampleTest {

    @Resource

    private UserMapper userMapper;
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }
    @Test
    public  void testDis() throws NoSuchAlgorithmException {
        String newPassword = DigestUtils.md5DigestAsHex(("yan" + "userpassword").getBytes());
        System.out.println(newPassword);


    }
}