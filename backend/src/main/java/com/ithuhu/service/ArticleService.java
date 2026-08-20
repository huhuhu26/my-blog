package com.ithuhu.service;

import com.ithuhu.pojo.DTO.Article;
import com.ithuhu.pojo.QueryParam;
import com.ithuhu.pojo.Result;

public interface ArticleService {
    Result publish(Article article);

    Result getlist(QueryParam queryParam);

    Result getById(Long id);

    Result update(Article article);

    Result delete(Long id);

}
