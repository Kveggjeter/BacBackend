package com.bac.bacbackend.application.routine.crawling;

import com.bac.bacbackend.domain.common.validators.ArticleUrlValidator;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import com.bac.bacbackend.domain.port.IBrowser;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import com.bac.bacbackend.domain.port.IWebSelectors;
import com.bac.bacbackend.domain.service.crawling.ArticleUrlStrategy;
import com.bac.bacbackend.domain.service.crawling.Crawler;
import com.bac.bacbackend.domain.service.crawling.CrawlingStrategy;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Implementation of a WebCrawler. This implementation functions as a base controller where each strategy and crawler
 * is made as a new object, instead of dependency injection. This is to ensure that the global variables they share
 * and need stay the same and are not muted by accident.
 */
@Component
public class NewsArticleCrawler implements WebCrawler {
    private final IWebSelectors webSelectors;
    private final IBrowser browser;
    private final INewsParamRepo repository;
    private final ArticleUrlValidator articleUrlValidator;

    public NewsArticleCrawler(IWebSelectors webSelectors, IBrowser browser, INewsParamRepo repository, ArticleUrlValidator articleUrlValidator) {
        this.webSelectors = webSelectors;
        this.browser = browser;
        this.repository = repository;
        this.articleUrlValidator = articleUrlValidator;
    }

    /**
     * This method instantiates all the components needed for crawling. Uses the {@link ArticleUrlStrategy}
     * that focuses on getting the urls in one visit without backtracking and moving pages.
     *
     * @param maxUrlsToCrawl max urls ideally want to save
     * @param propertyIndex the given index we are on in the iteration, decides which property to pick
     * @return a list containing which website that are to be scraped
     */
    public List<ArticleUrls> crawlWebsites(int maxUrlsToCrawl, int propertyIndex) {
        ScrapingProperties scrapingProperties = repository.select(propertyIndex);
        CrawlingStrategy<ArticleUrls> crawlingStrategy = new ArticleUrlStrategy(webSelectors, articleUrlValidator);
        Crawler<ArticleUrls> crawler = new Crawler<>(browser, crawlingStrategy);

        return crawler.crawl(scrapingProperties, maxUrlsToCrawl);
    }
}
