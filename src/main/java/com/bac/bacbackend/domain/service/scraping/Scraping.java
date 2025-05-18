package com.bac.bacbackend.domain.service.scraping;

import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.port.IBrowser;
import com.bac.bacbackend.domain.port.IFailedRepo;
import lombok.RequiredArgsConstructor;

/**
 * Responsible for driving the scraping process for a single article
 * <p>
 * {@link IBrowser} is an interface for the browser we use to automate our scraping with.
 * to change browser or setting, simply make an own implementation that implements {@link IBrowser}
 */
public class Scraping {

    private final IBrowser browser;
    private final ScrapingStrategy scrapingStrategy;
    private final IFailedRepo failedRepo;
    private final SaveScrapedArticle saveScrapedArticle;

    public Scraping(IBrowser browser, ScrapingStrategy scrapingStrategy, IFailedRepo failedRepo, SaveScrapedArticle saveScrapedArticle) {
        this.browser = browser;
        this.scrapingStrategy = scrapingStrategy;
        this.failedRepo = failedRepo;
        this.saveScrapedArticle = saveScrapedArticle;
    }

    /**
     * Starts a webdriver session that closes after scraping is done. Adds articles
     * to Redis. If we fail to collect anything from an article, that article is now considered a
     * failed article, and will be skipped if encountered again.
     *
     * @param scrapeContext data class that defines the scraped properties
     */
    public void scrape(ScrapeContext scrapeContext) {
        try {
            browser.start(scrapeContext.getArticleUrl());
            if(scrapingStrategy.doScrape(scrapeContext)) {
                saveScrapedArticle.save(scrapeContext);
                System.out.println("[" + Thread.currentThread().getName() + "]" + " Added article: " +  scrapeContext.getArticleUrl());
            } else {
                failedRepo.addToFail(scrapeContext.getArticleUrl());
                System.err.println("[" + Thread.currentThread().getName() + "]" + " Article failed: " + scrapeContext.getArticleUrl());
            }
        } finally {
            browser.stop();
        }
    }
}

