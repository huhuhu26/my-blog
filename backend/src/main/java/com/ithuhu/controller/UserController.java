package com.ithuhu.controller;


import com.ithuhu.pojo.Result;
import com.ithuhu.pojo.User;
import com.ithuhu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;

    //1用户注册
    @PostMapping("/register")
    public Result register(@Validated @RequestBody User user) {
        log.info("用户注册信息user: {}", user);
        return userService.register(user);
    }


    //2用户登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.info("用户登录信息user: {}", user);
        return userService.login(user);
    }


}
