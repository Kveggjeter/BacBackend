package com.bac.bacbackend.application.routine.crawling;

import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import java.util.List;

/**
 * Interface for webcrawling, primary use-case is collecting urls to scrape
 */
public interface WebCrawler {
    List<ArticleUrls> crawlWebsites(int maxUrlsToCrawl, int propertyIndex);
}
