package com.ithuhu.mapper;

import com.ithuhu.pojo.DTO.Article;
import com.ithuhu.pojo.DTO.ArticleDetailVO;
import com.ithuhu.pojo.DTO.ArticleListVO;
import com.ithuhu.pojo.QueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    //保存文章
    @Insert("insert into article (title, content, user_id, category_id) values (#{title}, #{content}, #{userId}, #{categoryId})")
    void save(Article article);

    //查询文章列表
    List<ArticleListVO> selectList(@Param("queryParam") QueryParam queryParam, @Param("userId") Long userId);

    //查询文章详情

    ArticleDetailVO getById(Long id);

    //更新文章
    @Update("update article set title = #{title}, content = #{content} ,category_id = #{categoryId} where id = #{id}")
    void update(Article article);

    //删除文章
    @Delete("delete from article where id = #{id}")
    void delete(Long id);

    //更新时判断文章
    @Select("select * from article where id = #{id}")
    Article getArticleById(Long id);
}
