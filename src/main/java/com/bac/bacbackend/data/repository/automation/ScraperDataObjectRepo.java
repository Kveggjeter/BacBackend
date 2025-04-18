package com.bac.bacbackend.data.repository.automation;

import com.bac.bacbackend.data.model.scraper.ScraperObjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScraperDataObjectRepo extends CrudRepository<ScraperObjectEntity, String> {
}
