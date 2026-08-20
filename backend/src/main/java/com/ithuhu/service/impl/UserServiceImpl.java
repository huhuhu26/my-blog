package com.ithuhu.service.impl;


import com.ithuhu.mapper.UserMapper;
import com.ithuhu.pojo.Result;
import com.ithuhu.pojo.User;
import com.ithuhu.service.UserService;
import com.ithuhu.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result register(User user) {
        //1判断输入信息是否合规  交给注解

        //2调用mapper接口判断用户是否已经存在
        if (userMapper.selectByUsername(user.getUsername()) != null) {
            return Result.error("用户已存在");
        }
        //3补齐创建时间
        user.setCreatedAt(java.time.LocalDateTime.now());
        //调用加密算法加密密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //3调用mapper接口存储用户信息
        userMapper.register(user);
        return Result.success();
    }

    @Override
    public Result login(User user) {
        User u = userMapper.selectByUsername(user.getUsername());
        //判断是否有该用户
        if(u == null){
            return Result.error("用户名或密码错误");
        }
        //2.密码校验：前端明文 user.getPassword() 和数据库密文 u.getPassword()对比
        if (!new BCryptPasswordEncoder().matches(user.getPassword(), u.getPassword())){
            return Result.error("用户名或密码错误");
        }
        //生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", u.getId());
        claims.put("username", u.getUsername());
        String token = JwtUtils.generateToken(claims);
        user.setToken(token);
        return Result.success(user);
    }
}
