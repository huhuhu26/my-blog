package com.ithuhu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ithuhu.mapper.ArticleMapper;
import com.ithuhu.pojo.Article;
import com.ithuhu.pojo.PageResult;
import com.ithuhu.pojo.QueryParam;
import com.ithuhu.pojo.Result;
import com.ithuhu.service.ArticleService;
import com.ithuhu.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    //发布文章
    @Override
    public Result publish(Article article) {
        if(article.getTitle()==null){
            return Result.error("标题不能为空");
        }
        if(article.getContent()==null)
            return Result.error("内容不能为空");
        //获取操作用户id
        article.setUserId(CurrentHolder.getCurrentUserId());
        //保存文章到数据库
        articleMapper.save(article);
        return Result.success("发布成功");
    }

    //分页查询文章列表
    @Override
    public Result getlist(QueryParam queryParam) {
        PageHelper.startPage(queryParam.getPage(), queryParam.getPageSize());
        List<Article> article = articleMapper.selectList();

        Page<Article> page = (Page<Article>) article;
        return Result.success(new PageResult(page.getTotal(),  page.getResult()));
    }
}
