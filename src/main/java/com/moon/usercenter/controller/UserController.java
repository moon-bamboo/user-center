package com.moon.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moon.usercenter.model.domain.User;
import com.moon.usercenter.model.domain.request.UserLoginRequest;
import com.moon.usercenter.model.domain.request.UserRegisterRequest;
import com.moon.usercenter.service.UserService;
import com.moon.usercenter.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static com.moon.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.moon.usercenter.constant.UserConstant.USER_LOGIN_STATUS;

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

    @GetMapping("/search")
    public List<User> searchUsers(String username,HttpServletRequest request){
        //判断用户是否为管理员，仅管理员可查询用户
        if(!isAdmin(request)){
            return new ArrayList<>();
        }

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        //将查询到的用户信息列表，全部脱敏
        return userList.stream().map(user -> userService.getSaftyUser(user)).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean deleteUsers(@RequestBody long id,HttpServletRequest request){
        if(id <= 0){
            return false;
        }
        if(!isAdmin(request)){
            return false;
        }


        return userService.removeById(id);
    }

    /**
     * 判断用户是否为管理员
     * @param request
     * @return true是管理员，false不是管理员
     */
    private boolean isAdmin(HttpServletRequest request){
        //判断用户是否为管理员
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATUS);
        if(user==null || user.getUserRole()!= ADMIN_ROLE){
            return false;
        }
        return true;
    }
}
