package com.bac.bacbackend.application.routine.scraping;

import com.bac.bacbackend.domain.common.ContentAnalysis;
import com.bac.bacbackend.domain.common.validators.SummaryValidator;
import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import com.bac.bacbackend.domain.port.IChrome;
import com.bac.bacbackend.domain.port.IFailedRepo;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import com.bac.bacbackend.domain.port.IWebSelectors;
import com.bac.bacbackend.domain.service.scraper.scraping.ArticleScrapingStrategy;
import com.bac.bacbackend.domain.service.scraper.scraping.SaveScrapedArticle;
import com.bac.bacbackend.domain.service.scraper.scraping.Scraping;
import com.bac.bacbackend.domain.service.scraper.scraping.ScrapingStrategy;
import org.springframework.stereotype.Component;

@Component
public class NewsArticleScraper implements Scraper {
    private final IWebSelectors webSelectors;
    private final SummaryValidator summaryValidator;
    private final ContentAnalysis contentAnalysis;
    private final INewsParamRepo repository;
    private final IChrome browser;
    private final IFailedRepo failedRepo;
    private final SaveScrapedArticle saveScrapedArticle;

    public NewsArticleScraper(IWebSelectors webSelectors, SummaryValidator summaryValidator, ContentAnalysis contentAnalysis, INewsParamRepo repository, IChrome browser, IFailedRepo failedRepo, SaveScrapedArticle saveScrapedArticle) {
        this.webSelectors = webSelectors;
        this.summaryValidator = summaryValidator;
        this.contentAnalysis = contentAnalysis;
        this.repository = repository;
        this.browser = browser;
        this.failedRepo = failedRepo;
        this.saveScrapedArticle = saveScrapedArticle;
    }

    @Override
    public void scrapeWebsite(ArticleUrls articleUrls, int propertyIndex) {
        ScrapingProperties scrapingProperties = repository.select(propertyIndex);
        ScrapeContext scrapeContext = new ScrapeContext(articleUrls, scrapingProperties);
        ScrapingStrategy scrapingStrategy = new ArticleScrapingStrategy(webSelectors, summaryValidator, contentAnalysis);
        Scraping scraping = new Scraping(browser, scrapingStrategy, failedRepo, saveScrapedArticle);

        scraping.scrape(scrapeContext);
        }

    }

