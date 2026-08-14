package com.ithuhu.service.impl;


import com.ithuhu.mapper.UserMapper;
import com.ithuhu.pojo.Result;
import com.ithuhu.pojo.User;
import com.ithuhu.service.UserService;
import com.ithuhu.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result register(User user) {
        //1判断输入信息是否合规
        if (user.getPassword() == null||user.getPassword().length()<6||user.getPassword().length()>50) {
            return Result.error("用户密码长度不合规");
        }
        if(user.getUsername() == null||user.getUsername().length()>20||user.getUsername().length()<3){
            return Result.error("用户名长度不合规");
        }
        //2调用mapper接口判断用户是否已经存在
        if (userMapper.selectByUsername(user.getUsername()) != null) {
            return Result.error("用户已存在");
        }
        //3补齐创建时间
        user.setCreatedAt(java.time.LocalDateTime.now());
        //3调用mapper接口存储用户信息
        userMapper.register(user);
        return Result.success();
    }

    @Override
    public Result login(User user) {
        User u = userMapper.selectByNameAndPass(user.getUsername(), user.getPassword());
        //判断是否有该用户
        if(u == null){
            return Result.error("用户名或密码错误");
        }
        //生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", u.getId());
        claims.put("username", u.getUsername());
        String token = JwtUtils.generateToken(claims);
        return Result.success(token);
    }
}
