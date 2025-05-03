package com.bac.bacbackend.domain.service.scraping;

import com.bac.bacbackend.domain.model.article.ScrapeContext;

/**
 * Interface for scraping strategy.
 */
public interface ScrapingStrategy {
    boolean doScrape(ScrapeContext scrapeContext);
}
