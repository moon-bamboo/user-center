package com.moon.usercenter.model.domain.request;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author 白月青竹
 */
@Data
public class UserLoginRequest implements Serializable {
    //防止序列化过程出现冲突
    public static final long serialVersionUID = -7682100598862794802L;
    //用户账号
    public String userAccount;
    //用户密码
    public String userPassword;
}
