package com.ithuhu.mapper;

import com.ithuhu.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //用户注册
    @Insert("insert into user(username, password, created_at, avatar) values(#{username}, #{password}, #{createdAt}, #{avatar})")
    public void register(User user);

    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);

    @Select("select * from user where username = #{username} and password = #{password}")
    User selectByNameAndPass(String username, String password);
}
