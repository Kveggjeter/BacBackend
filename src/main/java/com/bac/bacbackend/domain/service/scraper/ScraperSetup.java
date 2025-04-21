package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.model.article.ScrapeContext;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
import com.bac.bacbackend.domain.port.IArticleRepo;

/**
 * Base class for scraping. Saves content to the db. Not generic, since the goal of the scraping
 * is purely fetching news articles and displaying them.
 */
public abstract class ScraperSetup {

    private final IArticleRepo articleRepo;

    protected ScraperSetup(IArticleRepo articleRepo) { this.articleRepo = articleRepo; }

    /**
     * Common method for executing the scraping. Controller method. Naming done to not confuse with
     * "Scraper"
     *
     * @param articleUrls record class with urls for articles to be scraped and a connected picture
     * @param scrapeProps web selectors for scraping
     */
    protected final void execute(ArticleUrls articleUrls, ScrapeProps scrapeProps) {
        ScrapeContext scrapeContext = new ScrapeContext(articleUrls, scrapeProps);
        if(doScrape(scrapeContext)) save(scrapeContext);
        else System.out.println("Error");
    }

    /**
     * Starts the scraper, generic since logic may vary quite a bit. Must be included by every child class
     *
     * @param scrapeContext instructions to the scraper
     * @return a boolean check, for letting the method execute know if it can save the content safely or not
     */
    protected abstract boolean doScrape(ScrapeContext scrapeContext);

    /**
     * Gets the current threads name. Used primarily for logging purposes.
     *
     * @return return the name of the current thread.
     */
    protected String getName() { return Thread.currentThread().getName(); }

    /**
     * Checks is the current thread has been interrupted. Has a built-in logger.
     *
     * @param threadName name of the thread
     * @return boolean checker
     */
    protected boolean interruptCheck(String threadName) {
        if(Thread.currentThread().isInterrupted()) {
            System.err.println("[" + threadName + "] failed fetching");
            return true;
        }
        return false;
    }

    /**
     * Saving data to the db. Casting a runtime exception if failed.
     *
     * @param scrapeContext scraping instructions, but also holds the Article data values
     */
    private void save(ScrapeContext scrapeContext) {
        try {
            articleRepo.adder(scrapeContext.toArticle());
            System.out.println("[" + getName() + "] Saved: "
                    + scrapeContext.getUrl());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected issue: " + e.getMessage(), e);
        }
    }

}

