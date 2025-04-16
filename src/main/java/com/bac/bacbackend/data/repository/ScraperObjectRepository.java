package com.bac.bacbackend.data.repository;

import com.bac.bacbackend.domain.model.ScraperObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScraperObjectRepository extends CrudRepository<ScraperObject, String> {
}
