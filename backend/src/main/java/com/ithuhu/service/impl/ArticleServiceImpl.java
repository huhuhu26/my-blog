package com.ithuhu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ithuhu.mapper.ArticleMapper;
import com.ithuhu.pojo.DTO.Article;
import com.ithuhu.pojo.DTO.ArticleListVO;
import com.ithuhu.pojo.DTO.ArticleDetailVO;

import com.ithuhu.pojo.PageResult;
import com.ithuhu.pojo.QueryParam;
import com.ithuhu.pojo.Result;
import com.ithuhu.service.ArticleService;
import com.ithuhu.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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

    //分页查询所有符合条件的文章列表
    @Override
    public Result getlist(QueryParam queryParam) {
        PageHelper.startPage(queryParam.getPage(), queryParam.getPageSize());
        log.info("queryParam: {}", queryParam);
        Long userId = null;
        if(queryParam.isOnlyMine()){
            //获取用户id
            userId = CurrentHolder.getCurrentUserId();
        }
        List<ArticleListVO> articleListVO = articleMapper.selectList(queryParam, userId);

        Page<ArticleListVO> page = (Page<ArticleListVO>) articleListVO;
        return Result.success(new PageResult(page.getTotal(),  page.getResult()));
    }


    //根据id查单篇
    @Override
    public Result getById(Long id) {
        ArticleDetailVO articleDetailVO = articleMapper.getById(id);
        return Result.success(articleDetailVO);
    }

    @Override
    public Result update(Article article) {
        //判断文章是否是该用户的
        if(!articleMapper.getById(article.getId()).getUserId().equals(CurrentHolder.getCurrentUserId()))
            return Result.error("无权限修改");
        //判断文章是否存在
        if(article.getTitle()==null){
            return Result.error("文章标题不能为空");
        }
        if(article.getContent()==null)
            return Result.error("文章内容不能为空");
        Article articleById = articleMapper.getArticleById(article.getId());
        if(articleById==null)
            return Result.error("文章不存在");
        articleMapper.update(article);
        return Result.success("更新成功");
    }

    @Override
    public Result delete(Long id) {
        //判断文章是否是该用户的
        if(!articleMapper.getById(id).getUserId().equals(CurrentHolder.getCurrentUserId()))
            return Result.error("无权限删除");
        //判断文章是否存在
        if(articleMapper.getById(id)==null)
            return Result.error("文章不存在");
        articleMapper.delete(id);
        return Result.success("删除成功");
    }


}
