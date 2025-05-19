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
     * @param article article class from domain
     */
    @Override
    public void adder(Article article) {
        ArticleEntity articleEntity = ArticleEntity.fromDomain(article);
        add(articleEntity);
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
            articles.add(entity.toDomain());
        }
        return articles;
    }

}
