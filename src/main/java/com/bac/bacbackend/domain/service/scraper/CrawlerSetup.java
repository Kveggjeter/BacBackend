package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.port.IChrome;
import com.bac.bacbackend.domain.port.IWebSelectors;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for webcrawlers. Uses an instance of Chrome and webselectors to
 * navigate and fetch data from websites.
 *
 * @param <T> generic type to be inhabited by record classes
 */
public abstract class CrawlerSetup<T> {

    protected final IChrome browser;
    protected final IWebSelectors webSelectors;
    /**
     * The url for where the crawling starts
     */
    protected String startUrl;
    /**
     * Generic list for objects later to be crawled in other instances and saved to the db.
     */
    protected List<T> list = new ArrayList<>();

    protected CrawlerSetup(IChrome browser, IWebSelectors webSelectors) {
        this.browser = browser;
        this.webSelectors = webSelectors;
    }

    /**
     * Sets up necessary properties for scraping based on the inputted index.
     *
     * @param propertyIndex index for what properties the crawler should use
     */
    protected abstract void setProperties(int propertyIndex);

    /**
     * Starts the crawler logic and is abstracted since the logic may vary between
     * each URL.
     *
     * @param maxArticles max amount of articles allowed saved for the session
     */
    protected abstract void startCrawling(int maxArticles);

    /**
     *
     * Generic method for crawling. Resets the list and sets the properties, and
     * makes sure there is a common browser driver throughout the session.
     *
     * @param propertyIndex index for each selector
     * @param maxArticles max articles to be scraped
     * @return generic list containing info to later be scraped from
     */
    protected List<T> doCrawl(int propertyIndex, int maxArticles) {
        reset();
        setProperties(propertyIndex);
        browser.start(startUrl);
        try {
            System.out.println("Crawling webpage, please wait...");
            startCrawling(maxArticles);
        } finally {
            browser.stop();
        }
        System.out.println("Crawling finished, sending to scraper..");
        return list;
    }

    /**
     * Simply resets the list
     */
    private void reset() {
        list.clear();
    }
}
