package com.bac.bacbackend.domain.service.routine;

import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.service.scraper.Scraper;
import com.bac.bacbackend.domain.service.scraper.WebCrawler;
import java.util.List;

/**
 * Base class for bot-controllers. All bots collects and saves data to later be saved as an Article.
 * Therefor all bots uses the same initialization and benefit from having one common parent.
 */
public abstract class Bot extends MultiThreading {

    private final WebCrawler crawler;
    private final Scraper scraper;

    public Bot(Scraper scraper, WebCrawler crawler) {
        this.scraper = scraper;
        this.crawler = crawler;
    }

    /**
     * Recursive method for crawling and scraping the given articles. It being recursive allows for
     * easier throwing and continuing even when some parts fail (a sort of poor-mans backtracking).
     * The stack is not cause any trouble.
     *
     * @param propertyIndex the index of what webelement to be used for scraping and crawling
     * @param maxArticles max amount of articles to be scraped
     */
    protected void doStart(int propertyIndex, int maxArticles) {
        if (propertyIndex < 0) return;

        threadPool = null;
        try {
            createThreadPool();
            List<ArticleUrls> articleUrls = crawler.crawl(propertyIndex, maxArticles);

            if (articleUrls == null || articleUrls.isEmpty())
                System.out.println("Nothing new to add, continuing..");
            else {
                System.out.println("Adding " + articleUrls.size() + " articles to be scraped..");
                for (ArticleUrls a : articleUrls) {
                    threadPool.submit(() -> scraper.start(a, propertyIndex));
                }
            }
            stop(threadPool);
        } finally {
            shutdown();
        }
        sleep();
        doStart(propertyIndex - 1, maxArticles);
    }
}
