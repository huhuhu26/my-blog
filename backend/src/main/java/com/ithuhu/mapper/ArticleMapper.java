package com.ithuhu.mapper;

import com.ithuhu.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {

    //保存文章
    @Insert("insert into article (title, content, user_id) values (#{title}, #{content}, #{userId})")
    void save(Article article);

    //查询文章列表
    @Select("select * from article")
    List<Article> selectList();
}
