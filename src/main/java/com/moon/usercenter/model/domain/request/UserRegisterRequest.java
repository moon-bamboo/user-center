package com.moon.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author 白月青竹
 */
@Data
public class UserRegisterRequest implements Serializable {
    //防止序列化过程出现冲突
    public static final long serialVersionUID = 2602233935979690009L;
    //用户账号
    public String userAccount;
    //用户密码
    public String userPassword;
    //验证密码
    public String checkPassWord;

}
