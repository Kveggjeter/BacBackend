package com.bac.bacbackend.data.repository.automation;

import com.bac.bacbackend.data.model.article.CountryUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryDataUrl extends JpaRepository<CountryUrlEntity, String> {
}
