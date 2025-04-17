package com.bac.bacbackend.data.repository;

import com.bac.bacbackend.data.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, String> {
}
