package com.bac.bacbackend.domain.service.routine;

import com.bac.bacbackend.domain.model.scraper.ArticleData;
import com.bac.bacbackend.domain.service.scraper.Scraper;
import com.bac.bacbackend.domain.service.scraper.WebCrawler;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class BotController {

    private final Scraper scraper;
    private final WebCrawler crawler;
    private final int threadNumber = 3;

    public BotController(Scraper scraper, WebCrawler crawler) {
        this.scraper = scraper;
        this.crawler = crawler;
    }

    public void start(int n, int maxNum) {
        if (n < 0) {
            System.out.println("Webscraping has finished");
            return;
        }

        ExecutorService pool = null;
        try {
             pool = Executors.newFixedThreadPool(threadNumber);
             List<ArticleData> articles = crawler.crawl(n, maxNum);
             System.out.println("Sending articles to scraper...");

             for (ArticleData a : articles) {
                 pool.submit(() -> scraper.start(a, n));
             }

             pool.shutdown();
        try {
            if(!pool.awaitTermination(120, TimeUnit.SECONDS))
                pool.shutdownNow();
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }

        } finally {
            if (pool != null) pool.shutdown();
        }

        System.out.println("Scraping finished for index: " + n);
        start(n - 1, maxNum);
    }

}
