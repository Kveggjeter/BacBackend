package com.bac.bacbackend.application.routine.scraping;

import com.bac.bacbackend.domain.model.scraper.ArticleUrls;

/**
 * Interface for scraping websites given through the data class {@code ArticleUrls}
 */
public interface Scraper {
    void scrapeWebsite(ArticleUrls articleUrls, int propertyIndex);
}
