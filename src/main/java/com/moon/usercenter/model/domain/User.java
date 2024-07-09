package com.moon.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户登录账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户昵称，默认“未命名用户”
     */
    private String username;

    /**
     * 用户头像URL
     */
    private String avatarUrl;

    /**
     * 用户性别，默认0，表示未知性别
     */
    private Integer gender;

    /**
     * 用户电话号码
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户角色，默认为0，普通用户。1，管理员
     */
    private Integer userRole;

    /**
     * 用户状态，非空，默认1，表示正常
     */
    private Integer userStatus;

    /**
     * 用户创建时间
     */
    private Date createTime;

    /**
     * 用户修改时间
     */
    private Date updateTime;

    /**
     * 以逻辑删除替代物理删除，0表示正常，1表示被删除
     */
    @TableLogic
    private Integer deleted;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}