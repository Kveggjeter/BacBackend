package com.bac.bacbackend.application.routine.scraping;

import com.bac.bacbackend.domain.common.ContentAnalysis;
import com.bac.bacbackend.domain.common.validators.SummaryValidator;
import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import com.bac.bacbackend.domain.port.IBrowser;
import com.bac.bacbackend.domain.port.IFailedRepo;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import com.bac.bacbackend.domain.port.IWebSelectors;
import com.bac.bacbackend.domain.service.scraping.ArticleScrapingStrategy;
import com.bac.bacbackend.domain.service.scraping.SaveScrapedArticle;
import com.bac.bacbackend.domain.service.scraping.Scraping;
import com.bac.bacbackend.domain.service.scraping.ScrapingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Implementation of a Scraper. This implementation is a simple base controller that prioritizing instantiating objects
 * inside the methods instead of relaying on dependency injection
 * (similar to {@link com.bac.bacbackend.application.routine.crawling.NewsArticleCrawler}). Primarily done to combat
 * muting the global variables by accident.
 */
@RequiredArgsConstructor
@Component
public class NewsArticleScraper implements Scraper {
    private final IWebSelectors webSelectors;
    private final SummaryValidator summaryValidator;
    private final ContentAnalysis contentAnalysis;
    private final INewsParamRepo repository;
    private final IBrowser browser;
    private final IFailedRepo failedRepo;
    private final SaveScrapedArticle saveScrapedArticle;

    /**
     * This method instantiates all the components used for scraping. Uses the {@link ArticleScrapingStrategy}
     * that mainly focuses on visiting each article in its own window, instead of everything in one bulk
     * or under several tabs in Selenium.
     *
     * @param articleUrls received urls from the crawler that are to be scraped
     * @param propertyIndex the given index we are on in the iteration, decides which property to pick
     */
    @Override
    public void scrapeWebsite(ArticleUrls articleUrls, int propertyIndex) {
        ScrapingProperties scrapingProperties = repository.select(propertyIndex);
        ScrapeContext scrapeContext = new ScrapeContext(articleUrls, scrapingProperties);
        ScrapingStrategy scrapingStrategy = new ArticleScrapingStrategy(webSelectors, summaryValidator, contentAnalysis);
        Scraping scraping = new Scraping(browser, scrapingStrategy, failedRepo, saveScrapedArticle);

        scraping.scrape(scrapeContext);
    }
}

