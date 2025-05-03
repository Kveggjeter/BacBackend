package com.bac.bacbackend.data.repository.article;

import com.bac.bacbackend.data.model.article.ArticleEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for the article entity
 */
public interface ArticleDataRepo extends CrudRepository<ArticleEntity, String> {
}
