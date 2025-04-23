package com.bac.bacbackend.application.routine.crawling;

import com.bac.bacbackend.domain.common.validators.ArticleUrlValidator;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import com.bac.bacbackend.domain.port.IChrome;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import com.bac.bacbackend.domain.port.IWebSelectors;
import com.bac.bacbackend.domain.service.scraper.crawling.ArticleUrlStrategy;
import com.bac.bacbackend.domain.service.scraper.crawling.Crawler;
import com.bac.bacbackend.domain.service.scraper.crawling.CrawlingStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsArticleCrawler implements WebCrawler {
    private final IWebSelectors webSelectors;
    private final IChrome browser;
    private final INewsParamRepo repository;
    private final ArticleUrlValidator articleUrlValidator;


    public NewsArticleCrawler(IWebSelectors webSelectors,
                              IChrome browser,
                              INewsParamRepo repository,
                              ArticleUrlValidator articleUrlValidator) {
        this.webSelectors = webSelectors;
        this.browser = browser;
        this.repository = repository;
        this.articleUrlValidator = articleUrlValidator;
    }

    public List<ArticleUrls> crawlWebsites(int maxUrlsToCrawl, int propertyIndex) {
        ScrapingProperties scrapingProperties = repository.select(propertyIndex);
        CrawlingStrategy<ArticleUrls> crawlingStrategy = new ArticleUrlStrategy(webSelectors, articleUrlValidator);
        Crawler<ArticleUrls> crawler = new Crawler<>(browser, crawlingStrategy);

        return crawler.crawl(scrapingProperties, maxUrlsToCrawl);
    }
}
