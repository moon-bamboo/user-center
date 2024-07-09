package com.moon.usercenter.service;

import com.moon.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 白月青竹
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-07-05 18:19:36
*/

public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassWord 二次输入密码
     * @return 返回新用户的id
     */
    long userRegister(String userAccount,String userPassword,String checkPassWord);

    /**
     * 用户登录
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @return 返回用户的信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @return safetyUser
     */
    User getSaftyUser(User originUser);
}
