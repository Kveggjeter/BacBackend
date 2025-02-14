package com.bac.bacbackend.data.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class Bot extends StringBank {
    private final WebCrawler webCrawler;
    private final Scraper scraper;
    private final int threadNumber = 1;
    private final int maxArticles = 1;

    @Autowired
    public Bot(WebCrawler webCrawler, Scraper scraper) {
        this.webCrawler = webCrawler;
        this.scraper = scraper;
    }

    public void start() {
        List<WebCrawler.ArticleData> articles = webCrawler.startCrawling(maxArticles, anUrl, anTxtLocator, anTxtHref, anImgLocator, anImgHref);
        System.out.println("Sending articles to scraper...");
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);

        for (WebCrawler.ArticleData articleData : articles) {
            executorService.submit(() -> {
                scraper.scrape(articleData.articleUrl(), articleData.imgUrl(), anTitle, anSum);
            });
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        }
        catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        System.out.println("Scraping finished.");
    }
}
