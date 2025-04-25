package com.bac.bacbackend.application.routine;

import com.bac.bacbackend.application.routine.crawling.WebCrawler;
import com.bac.bacbackend.application.routine.scraping.Scraper;
import com.bac.bacbackend.application.threads.MultiThreading;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import java.util.List;

/**
 * Base class for bot-controllers. All bots collects and saves data to later be saved as an Article.
 * Therefor all bots uses the same initialization and benefit from having one common parent.
 */
public abstract class Bot extends MultiThreading {

    private final Scraper scraper;
    private final WebCrawler webCrawler;

    public Bot(Scraper scraper, WebCrawler webCrawler) {
        this.scraper = scraper;
        this.webCrawler = webCrawler;
    }

    public void doStart(int propertyIndex, int maxArticles) {
        if (propertyIndex < 0) return;
        threadPool = null;
        try {
            createThreadPool();
            List<ArticleUrls> articleUrls = webCrawler.crawlWebsites(maxArticles, propertyIndex);
            if (articleUrls == null || articleUrls.isEmpty())
                System.out.println("[" + getName() + " ] " + "Nothing new to add, continuing..");
            else {
                System.out.println("[" + getName() + " ] " + "Adding " + articleUrls.size() + " articles to be scraped..");
                for (ArticleUrls a : articleUrls) {
                    threadPool.submit(() -> scraper.scrapeWebsite(a, propertyIndex));
                    System.out.println("[" + getName() + " ] " + "Scraping: " + a.articleUrl());
                }
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
