package com.ithuhu.controller;


import com.ithuhu.pojo.Article;
import com.ithuhu.pojo.QueryParam;
import com.ithuhu.pojo.Result;
import com.ithuhu.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result publish(@RequestBody Article article) {
        log.info("发布文章内容：{}", article);

        return articleService.publish(article);
    }

    @GetMapping("/list")
    public Result list(QueryParam queryParam) {
        log.info("获取文章列表");

        return articleService.getlist(queryParam);
    }
}
