package com.liubin.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liubin.usercenter.common.BaseResponse;
import com.liubin.usercenter.common.ErrorCode;
import com.liubin.usercenter.common.ResultUtils;
import com.liubin.usercenter.exception.BusinessException;
import com.liubin.usercenter.model.User;
import com.liubin.usercenter.model.request.UserLoginResuest;
import com.liubin.usercenter.model.request.UserRegisterResuest;
import com.liubin.usercenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.liubin.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.liubin.usercenter.contant.UserConstant.USER_LOGIN_STATE;
import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @ApiOperation("注册接口")
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterResuest userRegisterResuest){
        if(userRegisterResuest==null){
            // 表示是请求参数有误
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String getUserAccount=userRegisterResuest.getUserAccount();
        String getUserPassword=userRegisterResuest.getUserPassword();
        String getCheckPassword=userRegisterResuest.getCheckPassword();
        String getplanetCode = userRegisterResuest.getPlanetCode();
        if(StringUtils.isAllBlank(getUserAccount,getUserPassword,getCheckPassword,getplanetCode)){
            return null;
        }
        long userId = userService.userRegister(getUserAccount, getUserPassword, getCheckPassword,getplanetCode);
        return ResultUtils.success(userId);
    }

    @GetMapping("/current")
    @ApiOperation("获取登陆舰用户的信息")
    public  BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser =(User) attribute;
        if(currentUser == null){
            return null;
        }
        // 由于返回的用户 的信息可能 随时修改，就重新取库中查询
        long userId = currentUser.getId();
        // todo
        User byId = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(byId);
        return ResultUtils.success(safetyUser);
    }

    @PostMapping("/login")
    @ApiOperation("登陆接口")
    public BaseResponse<User> userLogin(@RequestBody UserLoginResuest userLoginResuest,HttpServletRequest request){
        if(userLoginResuest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String getUserAccount=userLoginResuest.getUserAccount();
        String getUserPassword=userLoginResuest.getUserPassword();
        if(StringUtils.isAllBlank(getUserAccount,getUserPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码和确认密码不一致");
        }
        User dologin = userService.dologin(getUserAccount, getUserPassword, request);
        return ResultUtils.success(dologin);
    }
    @PostMapping("/logout")
    @ApiOperation("退出登陆接口")
    @CrossOrigin
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if(request==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        int i = userService.userLogout(request);
        return ResultUtils.success(i);
    }
    @GetMapping("/search")
    @ApiOperation("查询接口")
    public BaseResponse<List<User>> searchUsers(String username,HttpServletRequest request){
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH,"用户不是管理员 无权限");
        }
        List<User> users = userService.searchUsers(username);
        //进行 脱敏处理  一般 写在 service 中
        List<User> collect = users.stream().map(user -> {
            return userService.getSafetyUser(user);
        }).collect(Collectors.toList());
        return ResultUtils.success(collect);
    }
    @PostMapping("/delete")
    @ApiOperation("删除接口")
    public BaseResponse<Boolean> deleteUsers(@RequestBody long id,HttpServletRequest request){
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH,"不是管理员 无权限");
        }
        if(id<0){
            return null;
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }
    //仅管理员才可以登陆
    private boolean isAdmin(HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user=(User)attribute;
        if(user ==null ||user.getUserRole()!=ADMIN_ROLE){
            throw new BusinessException(ErrorCode.NO_AUTH,"不是管理员");
        }
        return true;
    }

}
