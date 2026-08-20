package com.ithuhu.controller;


import com.ithuhu.pojo.DTO.Article;
import com.ithuhu.pojo.QueryParam;
import com.ithuhu.pojo.Result;
import com.ithuhu.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/add")
    public Result publish(@RequestBody Article article) {
        log.info("发布文章内容：{}", article);

        return articleService.publish(article);
    }

    @GetMapping("/list")
    public Result list(QueryParam queryParam) {
        log.info("获取文章列表");
        return articleService.getlist(queryParam);
    }


    @GetMapping("/detail/{id}")
    public Result getById(@PathVariable Long id) {
        log.info("获取文章详情");
        return articleService.getById(id);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id,@RequestBody Article article) {
        log.info("更新文章详情");
        article.setId(id);
        return articleService.update(article);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        log.info("删除文章");
        return articleService.delete(id);
    }
}
