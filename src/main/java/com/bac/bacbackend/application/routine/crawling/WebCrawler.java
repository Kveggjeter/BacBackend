package com.bac.bacbackend.application.routine.crawling;

import com.bac.bacbackend.domain.model.scraper.ArticleUrls;

import java.util.List;

public interface WebCrawler {
    List<ArticleUrls> crawlWebsites(int maxUrlsToCrawl, int propertyIndex);
}
