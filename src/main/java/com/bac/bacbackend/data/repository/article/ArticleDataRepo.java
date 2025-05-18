package com.bac.bacbackend.data.repository.article;

import com.bac.bacbackend.data.model.article.ArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the article entity
 */
@Repository
public interface ArticleDataRepo extends CrudRepository<ArticleEntity, String> {
}
