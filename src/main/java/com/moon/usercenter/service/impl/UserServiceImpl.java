package com.moon.usercenter.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.usercenter.mapper.UserMapper;
import com.moon.usercenter.model.domain.User;
import com.moon.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 白月青竹
* @description 针对表【user(用户)】的数据库操作Service的实现
* @createDate 2024-07-05 18:19:36
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;
    /**
     * 盐值 混淆密码，用于加密
     */
    private static final String salt = "moon";

    /**
     * 用户登录态
     */
    public static final String USER_LOGIN_STATUS="userLoginStatus";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassWord) {
        //1.校验三个值是否合法
        //非空校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassWord)){
            return -1;
        }
        //账号长度与密码长度
        if(userAccount.length()<4){
            return -1;
        }
        if(userPassword.length()<8||checkPassWord.length()<8) {
            return -1;
        }
        //账号不能包含特殊字符(或许有待完善)
        String validPattern = "\\pP+|\\pS+|\\pM+|\\pZ+|\\pC+|\\s+";//[`~!@#$%^&*()+=|{}':;,\\.<>/?！￥…（）—【】‘；：”“’。，、？s]
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            return -1;
        }
        //两次输入的密码不重复
        if(!userPassword.equals(checkPassWord)){
            return -1;
        }
        //账号不能重复
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("user_account",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count > 0) {
            return -1;
        }

        //2.密码加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((salt + userPassword).getBytes());

        //3.向数据库插入用户信息
        User user=new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        boolean saveResult = this.save(user);
        if(!saveResult){//判断是否成功保存user
            return -1;
        }

        //4.返回用户id
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {

        //1.校验传入的值是否合法
        //非空校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        //账号长度与密码长度
        if(userAccount.length()<4){
            return null;
        }
        if(userPassword.length()<8) {
            return null;
        }
        //账号不能包含特殊字符(或许有待完善)
        String validPattern = "\\pP+|\\pS+|\\pM+|\\pZ+|\\pC+|\\s+";//[`~!@#$%^&*()+=|{}':;,\\.<>/?！￥…（）—【】‘；：”“’。，、？s]
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            return null;
        }
        //2.密码加密 与 账号是否存在
        String encryptedPassword = DigestUtils.md5DigestAsHex((salt + userPassword).getBytes());
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("user_account",userAccount);
        queryWrapper.eq("user_password",encryptedPassword);
        User user = userMapper.selectOne(queryWrapper);
        //账号密码匹配失败
        if(user==null) {
            log.info("User login failed,user account or password can't match.");
            return null;
        }
        //3.记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATUS,user);
        //4.脱敏
        User safetyUser=new User();
        safetyUser.setId(user.getId());//用户id
        safetyUser.setUserAccount(user.getUserAccount());//用户账号
        //safetyUser.setUserPassword("");//不返回用户密码
        safetyUser.setUsername(user.getUsername());//用户昵称
        safetyUser.setAvatarUrl(user.getAvatarUrl());//用户头像URL
        safetyUser.setGender(user.getGender());//用户性别
        safetyUser.setPhone(user.getPhone());//用户手机号
        safetyUser.setEmail(user.getEmail());//用户email
        safetyUser.setUserStatus(user.getUserStatus());//用户状态
        safetyUser.setCreateTime(user.getCreateTime());//创建时间
        safetyUser.setUpdateTime(user.getUpdateTime());//最后更新时间
        //safetyUser.setDeleted(0);//不返回Deleted字段


        return safetyUser;
    }
}




