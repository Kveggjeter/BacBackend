package com.bac.bacbackend.domain.service.scraper.scraping;

import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.port.IChrome;
import com.bac.bacbackend.domain.port.IFailedRepo;

/**
 * Responsible for driving the scraping process for a single article
 * <p>
 * {@link IChrome} is an interface for the browser we use to automate our scraping with.
 * to change browser or setting, simply make an own implementation that implements {@link IChrome}
 */
public class Scraping {

    private final IChrome browser;
    private final ScrapingStrategy scrapingStrategy;
    private final IFailedRepo failedRepo;
    private final SaveScrapedArticle saveScrapedArticle;

    public Scraping(IChrome browser, ScrapingStrategy scrapingStrategy, IFailedRepo failedRepo, SaveScrapedArticle saveScrapedArticle) {
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
        browser.start(scrapeContext.getArticleUrl());
        try {
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

