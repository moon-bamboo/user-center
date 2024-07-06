package com.moon.usercenter.controller;

import com.moon.usercenter.model.domain.User;
import com.moon.usercenter.model.domain.request.UserLoginRequest;
import com.moon.usercenter.model.domain.request.UserRegisterRequest;
import com.moon.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户控制器
 * @author 白月青竹
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        //@RequestBody，让springMVC识别对象，使得前端传来的json对象可以与userRegisterRequest对应的上
        if(userRegisterRequest==null){
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassWord = userRegisterRequest.getCheckPassWord();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassWord)){
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassWord);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        //@RequestBody，让springMVC识别对象，使得前端传来的json对象可以与userRegisterRequest对应的上
        if(userLoginRequest==null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }
}
