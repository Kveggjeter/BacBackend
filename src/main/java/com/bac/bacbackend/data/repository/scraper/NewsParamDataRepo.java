package com.bac.bacbackend.data.repository.scraper;

import com.bac.bacbackend.data.model.scraper.NewsParamEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsParamDataRepo extends CrudRepository<NewsParamEntity, String> {
}
