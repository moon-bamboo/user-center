package com.moon.usercenter.service;

import com.moon.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户服务测试
 * @author 白月青竹
 */

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testAddUser(){

        User user = new User();
        user.setUserAccount("test0");
        user.setUserPassword("test0");
        user.setUsername("test0");
        user.setAvatarUrl("https://avatars.githubusercontent.com/u/134479177?v=4");
        user.setPhone("test0");
        user.setEmail("test0");

        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister(){
        String userAccount="test1_";
        String userPassword="test1111";
        String checkPassword="test1111";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount="test1-";
        userPassword="test1111";
        checkPassword="test1111";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount="test1……";
        userPassword="test1111";
        checkPassword="test1111";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount="test1￥";
        userPassword="test1111";
        checkPassword="test1111";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount="test1，";
        userPassword="test1111";
        checkPassword="test11111";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount="test1.0";
        userPassword="test1111";
        checkPassword="test1111";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
    }

}