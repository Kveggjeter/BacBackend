package com.bac.bacbackend.data.repository.article;

import com.bac.bacbackend.data.model.article.ArticleEntity;
import org.springframework.data.repository.CrudRepository;

public interface ArticleDataRepo extends CrudRepository<ArticleEntity, String> {
}
