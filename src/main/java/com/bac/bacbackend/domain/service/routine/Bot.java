package com.bac.bacbackend.domain.service.routine;

import com.bac.bacbackend.domain.model.scraper.ArticleData;
import com.bac.bacbackend.domain.service.scraper.Scraper;
import com.bac.bacbackend.domain.service.scraper.WebCrawler;
import java.util.List;

public abstract class Bot extends MultiThreading {

    private final WebCrawler crawler;
    private final Scraper scraper;

    public Bot(Scraper scraper, WebCrawler crawler) {
        this.scraper = scraper;
        this.crawler = crawler;
    }

    protected void doStart(int n, int max) {
        if (n < 0) return;

        pool = null;
        try {
            threadPool();
            List<ArticleData> article = crawler.crawl(n, max);

            if (article == null || article.isEmpty())
                System.out.println("Nothing new to add, continuing..");
            else {
                System.out.println("Adding " + article.size() + " articles to be scraped..");
                for (ArticleData a : article) {
                    pool.submit(() -> scraper.start(a, n));
                }
            }
            stop(pool);
        } finally {
            shutdown();
        }
        sleep();
        doStart(n - 1, max);
    }

}
