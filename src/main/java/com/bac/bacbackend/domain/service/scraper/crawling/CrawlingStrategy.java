package com.bac.bacbackend.domain.service.scraper.crawling;

import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;

import java.util.List;

public interface CrawlingStrategy<T> {
    List<T> extractUrls(ScrapingProperties scrapingProperties, int maxUrlsToCrawl);
}
