package com.bac.bacbackend.data.repository.article;

import com.bac.bacbackend.data.model.article.FailedEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the failed articles
 */
@Repository
public interface FailedDataRepo extends CrudRepository<FailedEntity, String> {
}
