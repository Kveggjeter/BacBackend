package com.bac.bacbackend.data.repository.article;

import com.bac.bacbackend.data.model.article.ArticleEntity;
import com.bac.bacbackend.data.repository.common.DataSourceHandler;
import com.bac.bacbackend.domain.model.article.Article;
import com.bac.bacbackend.domain.port.IArticleRepo;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Article repository, extending the generic DatasourceHandler and implements an interface used on the domain layer.
 * Mainly contains methods to "translate" the dataclass found on the domain to the entity in the data architecture,
 * but also echoes some of the base methods found in {@link DataSourceHandler} for usage in domain.
 */
@Component
public class ArticleRepo extends DataSourceHandler<ArticleEntity, String> implements IArticleRepo {

    /**
     * Mandatory constructor for putting the repository as prioritized
     * @param repo the repository for article
     */
    public ArticleRepo(ArticleDataRepo repo) { super(repo); }

    /**
     * Translating the domain article class to the entity class
     * @param a article class from domain
     */
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

    /**
     * Translating the entity article class to the domain class
     * @param e the entity class that are to be transferred to domain
     * @return aN article class used in the domain layer
     */
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

    /**
     * Check if an id exist in Redis
     * @param id the identification on what we want to check if exists
     * @return if the queried string exists
     */
    @Override
    public boolean exists(String id) {
        return existById(id);
    }

    /**
     * Fetching articles from Redis using method from parent class {@link DataSourceHandler}, {@code findAll()},
     * translating it into the domain data class so it can be handled there.
     * @return A list of ArticleData
     */
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
