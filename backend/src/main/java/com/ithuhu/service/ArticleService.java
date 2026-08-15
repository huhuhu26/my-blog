package com.ithuhu.service;

import com.ithuhu.pojo.Article;
import com.ithuhu.pojo.QueryParam;
import com.ithuhu.pojo.Result;

public interface ArticleService {
    Result publish(Article article);

    Result getlist(QueryParam queryParam);
}
