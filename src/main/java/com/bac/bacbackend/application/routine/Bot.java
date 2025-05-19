package com.bac.bacbackend.application.routine;

import com.bac.bacbackend.application.routine.crawling.WebCrawler;
import com.bac.bacbackend.application.routine.scraping.Scraper;
import com.bac.bacbackend.application.threads.MultiThreading;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.port.IBrowser;
import com.bac.bacbackend.domain.service.crawling.Crawler;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Base class for bot-controllers. All bots collects and saves data to later be saved as an Article.
 * Therefor all bots uses the same initialization and benefit from having one common parent.
 */
@Component
public abstract class Bot extends MultiThreading {

    private final Scraper scraper;
    private final WebCrawler webCrawler;

    /**
     * Constructor for later lending methods to other bots. Not with the intention of making an own instance,
     * this should be extended if used.
     *
     * @param scraper {@link Scraper}
     * @param webCrawler {@link Crawler}
     * @param browser {@link com.bac.bacbackend.data.repository.browser.Browser}
     */
    protected Bot(Scraper scraper, WebCrawler webCrawler, IBrowser browser) {
        super(browser);
        this.scraper = scraper;
        this.webCrawler = webCrawler;
    }

    /**
     * Common starting method that uses recursion for iterating through each list of articles. There is little risk for
     * a stack overflow with the amount of actions that will be taken. The recursion allows for using the variables
     * more freely, the lambda function inside the {@code forEach) loop does for example need static instances,
     * but ideally we don't want to give away a place in memory for neither of these variables.
     * <p>
     * By the usage of its parent class {@link MultiThreading}, it starts a ThreadPool that run together with
     * spring boot's Tomcat. Both for taking load off the other instances in the application but also as option
     * for effective scraping.
     * @param propertyIndex propertyIndex the amount of news-source we have to go through
     * @param maxArticles the max amount of articles to be scraped
     */
    protected void doStart(int propertyIndex, int maxArticles) {
        if (propertyIndex < 0) return;
        threadPool = null;
        try {
            createThreadPool();
            try {
                List<ArticleUrls> articleUrls = webCrawler.crawlWebsites(maxArticles, propertyIndex);
                if (articleUrls == null || articleUrls.isEmpty())
                    System.out.println("[" + getName() + " ] " + "Nothing new to add, continuing..");
                else {
                    System.out.println("[" + getName() + " ] "
                            + "Adding " + articleUrls.size()
                            + " articles to be scraped..");
                    for (ArticleUrls a : articleUrls) {
                        threadPool.submit(() -> scraper.scrapeWebsite(a, propertyIndex));
                        System.out.println("[" + getName() + " ] " + "Scraping: " + a.articleUrl());
                    }
                }
            } catch (Exception e) {
                System.err.println("[" + getName() + " ] " + "Exception occurred: " + e.getMessage());
            }
            stop(threadPool);
        } finally {
            System.out.println("[" + getName() + " ] " + "Shutting down...");
            shutdown();
        }
        sleep();
        doStart(propertyIndex - 1, maxArticles);
    }
}
