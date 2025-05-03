package com.bac.bacbackend.domain.service.crawling;

import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import java.util.List;

/**
 *
 * @param <T> The dataclass used for saving articles. Change out with preferred data class
 */
public interface CrawlingStrategy<T> {
    List<T> extractUrls(ScrapingProperties scrapingProperties, int maxUrlsToCrawl);
}
