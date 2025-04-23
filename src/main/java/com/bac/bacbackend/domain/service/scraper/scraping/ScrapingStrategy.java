package com.bac.bacbackend.domain.service.scraper.scraping;

import com.bac.bacbackend.domain.model.article.ScrapeContext;

public interface ScrapingStrategy {
    boolean doScrape(ScrapeContext scrapeContext);
}
