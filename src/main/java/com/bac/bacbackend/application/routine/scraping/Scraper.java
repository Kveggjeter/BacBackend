package com.bac.bacbackend.application.routine.scraping;

import com.bac.bacbackend.domain.model.scraper.ArticleUrls;

public interface Scraper {
    void scrapeWebsite(ArticleUrls articleUrls, int propertyIndex);
}
