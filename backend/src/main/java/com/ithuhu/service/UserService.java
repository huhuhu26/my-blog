package com.ithuhu.service;

import com.ithuhu.pojo.Result;
import com.ithuhu.pojo.User;

public interface UserService {
    //注册
    public Result register(User user);

    //登录
    public Result login(User user);
}
