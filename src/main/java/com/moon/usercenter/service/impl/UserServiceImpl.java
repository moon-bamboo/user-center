package com.moon.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.usercenter.mapper.UserMapper;
import com.moon.usercenter.model.domain.User;
import com.moon.usercenter.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

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
        final String salt = "moon";
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
}




