package com.bac.bacbackend.data.repository.article;

import com.bac.bacbackend.data.model.article.FailedEntity;
import org.springframework.data.repository.CrudRepository;

public interface FailedDataRepo extends CrudRepository<FailedEntity, String> {
}
