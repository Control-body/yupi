package com.liubin.usercenter.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liubin.usercenter.common.ErrorCode;
import com.liubin.usercenter.exception.BusinessException;
import com.liubin.usercenter.model.User;
import com.liubin.usercenter.service.UserService;
import com.liubin.usercenter.mapper.UserMapper;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.DigestInputStream;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.liubin.usercenter.contant.UserConstant.USER_LOGIN_STATE;

/**
* @author luer
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2022-03-18 21:14:12
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    private UserMapper userMapper;
    /**
     * 用户登陆态件
     */
    /**
     * 盐值混淆密码
     */
    private static final String SALT="liubin";//加密加盐为了 让加密后的数值 更加混乱
    // 用户注册
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            // todo 修改为自定义异常
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
//        密码必须大于 八位 用户名称 必须大于4位
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号太短");
        }
        if(userPassword.length()<8||checkPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码小于八位");
        }
        if(planetCode.length() > 5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号太长");
        }
        //账号 不能包含 特殊字符 去网络上找正则表达式
        String validPattern="[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：” “’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户包含特殊字符");
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = baseMapper.selectCount(queryWrapper);
        if(count>0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户已经存在");
        }

        //星球编号不能重复
        queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("planetCode",planetCode);
        count = baseMapper.selectCount(queryWrapper);
        if(count>0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球账号已经重复");
        }

        //密码和 校验密码 相同
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"确认密码和密码不一致");
        }
        //加密
        String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        user.setPlanetCode(planetCode);
        boolean save = this.save(user);
        if(!save){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"添加错误");
        }
        return user.getId();
    }

    @Override
    public User dologin(String userAccount, String userPassword,HttpServletRequest request) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户长度少于四位");
        }
        if(userPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度大于八位");
        }

        //账号 不能包含 特殊字符 去网络上找正则表达式
        String validPattern="[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：” “’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数包含特殊字符");
        }

        String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("userAccount",userAccount);
        objectQueryWrapper.eq("userPassword",newPassword);
        User user = userMapper.selectOne(objectQueryWrapper);
        if(user==null){
            log.info("user login failed" );
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"登陆失败嘻嘻嘻");
        }

        //用户脱敏
        User safetyUser = this.getSafetyUser(user);
        //记录这个 用户的 登陆状态 也是脱敏后的 user实体类
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);

        return safetyUser;//脱敏后的
    }

    @Override
    public List<User> searchUsers(String username) {
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        if(StringUtils.isNoneBlank(username)){
            objectQueryWrapper.like("username",username);
        }
        //这里没有做 脱敏 处理 先写在前面
        return userMapper.selectList(objectQueryWrapper);
    }

    /**
     * 用户 脱敏
     * @param user
     * @return
     */
    public  User getSafetyUser(User user) {
        if(user == null){
            return null;
        }
        User safetUser = new User();
        safetUser.setId(user.getId());
        safetUser.setUsername(user.getUsername());
        safetUser.setUserAccount(user.getUserAccount());
        safetUser.setAvatarUrl(user.getAvatarUrl());
        safetUser.setPhone(user.getPhone());
        safetUser.setGender(user.getGender());
        // safetUser.setUserPassword("");密码不能返回
        safetUser.setEmail(user.getEmail());
        safetUser.setUserStatus(user.getUserStatus());
        safetUser.setCreateTime(new Date());
        safetUser.setUpdateTime(new Date());
        safetUser.setIsDelete(0);
        safetUser.setUserRole(user.getUserRole());
        safetUser.setPlanetCode(user.getPlanetCode());
        return safetUser;
    }

    /**
     *  推出登陆接口
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;

    }
}




