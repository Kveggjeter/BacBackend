package com.bac.bacbackend.domain.port;

import com.bac.bacbackend.domain.model.article.Article;

public interface IArticleRepo {
    void adder(Article article);
    boolean exists(String id);
}
