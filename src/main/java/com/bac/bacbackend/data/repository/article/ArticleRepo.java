package com.bac.bacbackend.data.repository.article;

import com.bac.bacbackend.data.model.article.ArticleEntity;
import com.bac.bacbackend.data.repository.common.DataSourceHandler;
import com.bac.bacbackend.domain.common.DataMapper;
import com.bac.bacbackend.domain.model.article.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleRepo extends DataSourceHandler<ArticleEntity, String> implements com.bac.bacbackend.domain.port.IArticleRepo {

    public ArticleRepo(ArticleDataRepo repo) { super(repo); }

    @Override
    public void adder(Article a) {
        add(DataMapper.toEntity(a, ArticleEntity.class));
    }

    @Override
    public boolean exists(String id) {
        return existById(id);
    }
}
