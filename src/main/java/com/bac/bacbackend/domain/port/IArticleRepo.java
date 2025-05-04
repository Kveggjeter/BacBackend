package com.bac.bacbackend.domain.port;

import com.bac.bacbackend.domain.model.article.Article;
import java.util.List;

/**
 * Interface used for adding and checking if the article exist
 */
public interface IArticleRepo {
    void adder(Article article);
    boolean exists(String id);
    List<Article> news();
}
