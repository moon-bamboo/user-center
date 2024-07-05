package com.moon.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.usercenter.model.domain.User;
import com.moon.usercenter.mapper.UserMapper;
import com.moon.usercenter.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author 白月青竹
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-07-05 18:19:36
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




