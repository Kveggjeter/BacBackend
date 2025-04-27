package com.bac.bacbackend.domain.service.scraper.crawling;

import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import com.bac.bacbackend.domain.port.IChrome;
import java.util.List;

public class Crawler<T> {
    private final IChrome browser;
    private final CrawlingStrategy<T> crawlingStrategy;

    public Crawler(IChrome browser, CrawlingStrategy<T> crawlingStrategy) {
        this.browser = browser;
        this.crawlingStrategy = crawlingStrategy;
    }

    public List<T> crawl(ScrapingProperties scrapingProperties, int maxUrlsToCrawl) {
        try {
            browser.start(scrapingProperties.url());
            System.out.println("Crawling webpage, please wait: " + scrapingProperties.url());
            return crawlingStrategy.extractUrls(scrapingProperties, maxUrlsToCrawl);
        } finally {
            browser.stop();
            System.out.println("Crawling finished, sending to scraper: " + scrapingProperties.url());
        }
    }
}
