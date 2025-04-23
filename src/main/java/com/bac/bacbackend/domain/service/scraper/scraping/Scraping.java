package com.bac.bacbackend.domain.service.scraper.scraping;

import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.port.IChrome;
import com.bac.bacbackend.domain.port.IFailedRepo;

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

    public void scrape(ScrapeContext scrapeContext) {
        browser.start(scrapeContext.getArticleUrl());
        try {
            System.out.println("Scraping started, please wait...");
            if(scrapingStrategy.doScrape(scrapeContext))
                try {
                    saveScrapedArticle.save(scrapeContext);
                    System.out.println("Scraping finished, added: " + scrapeContext.getArticleUrl() + " to Articles");
                } catch (Exception e) {
                    System.err.println("Unexpected fault, could not save " + e.getMessage());
                }
            else System.err.println("Could not save from: " + scrapeContext.getArticleUrl());

        } catch (Exception e) {
            System.err.println("Failed to extract info from: "
                    + scrapeContext.getArticleUrl()
                    + ": "
                    + e.getMessage());
            System.out.println("Adding: " + scrapeContext.getArticleUrl() + " to Failed articles" );
            failedRepo.addToFail(scrapeContext.getArticleUrl());
        } finally {
            browser.stop();
        }
    }
}

