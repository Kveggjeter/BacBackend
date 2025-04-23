package com.bac.bacbackend.data.repository.article;

import com.bac.bacbackend.data.model.article.ArticleEntity;
import com.bac.bacbackend.data.repository.common.DataSourceHandler;
import com.bac.bacbackend.domain.model.article.Article;
import com.bac.bacbackend.domain.port.IArticleRepo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleRepo extends DataSourceHandler<ArticleEntity, String> implements IArticleRepo {

    public ArticleRepo(ArticleDataRepo repo) { super(repo); }

    @Override
    public void adder(Article a) {
        ArticleEntity e = new ArticleEntity();

        e.setId(a.id());
        e.setSourceName(a.sourceName());
        e.setTitle(a.title());
        e.setSummary(a.summary());
        e.setCity(a.city());
        e.setCountry(a.country());
        e.setContinent(a.continent());
        e.setCategory(a.category());
        e.setX(a.x());
        e.setY(a.y());
        e.setImgUrl(a.imgUrl());

        add(e);
    }

    private Article toDomain(ArticleEntity e) {
        return new Article(
                e.getId(),
                e.getSourceName(),
                e.getTitle(),
                e.getSummary(),
                e.getCity(),
                e.getCountry(),
                e.getContinent(),
                e.getCategory(),
                e.getX(),
                e.getY(),
                e.getImgUrl()
        );
    }

    @Override
    public boolean exists(String id) {
        return existById(id);
    }

    @Override
    public List<Article> news() {
        List<Article> articles = new ArrayList<>();
        for (ArticleEntity entity : findAll()) {
            if (entity == null) {
                continue;
            }
            articles.add(toDomain(entity));
        }
        return articles;
    }
}
