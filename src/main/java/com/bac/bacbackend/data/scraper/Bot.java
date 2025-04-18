package com.bac.bacbackend.data.scraper;

import com.bac.bacbackend.data.scraper.config.WebSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The websetter can be abstracted yet another level, by making new list of each instances so we dont need to
 * iterate and choose index for each array. It matches some-what magically now because we've been adding it that way
 * in memory throughout, but it's pretty fragile.
 * TODO: Reduce the immense parameterization going on, maybe even rewrite the whole thing
 */
@Component
public class Bot {
    private final WebCrawler webCrawler;
    private final WebSetter wbs;
    private final Scraper scraper;
    private final int threadNumber = 5;

    @Autowired
    public Bot(WebCrawler webCrawler, Scraper scraper, WebSetter wbs) {
        this.webCrawler = webCrawler;
        this.scraper = scraper;
        this.wbs = wbs;
    }

    public void start(int n, int maxNum) {

        if (n < 0) {
            System.out.println("Webscraping has finished.");
            return;
        }

        ArrayList<WebCrawler.ArticleData> articles = webCrawler.startCrawling(n, maxNum);
        System.out.println("Sending articles to scraper...");
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);

        for (WebCrawler.ArticleData articleData : articles) {
            executorService.submit(() -> {
                scraper.scrape(articleData.articleUrl(), articleData.imgUrl(), wbs.make(n));
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
            System.out.println("Scraping finished for index: " + n);
            start(n - 1, maxNum);
    }

//    public void startSingle() {
//
//        List<WebCrawler.ArticleData> articles = webCrawler.startCrawling(
//                maxArticles, anUrl, anTxtLocator, anTxtHref, anImgLocator, anImgHref
//        );
//        System.out.println("Sending articles to scraper...");
//        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
//
//        for (WebCrawler.ArticleData articleData : articles) {
//            executorService.submit(() -> {
//                scraper.scrape(articleData.articleUrl(), articleData.imgUrl(), anTitle, anSum);
//            });
//        }
//
//        executorService.shutdown();
//        try {
//            if (!executorService.awaitTermination(120, TimeUnit.SECONDS)) {
//                executorService.shutdownNow();
//            }
//        }
//        catch (InterruptedException e) {
//            executorService.shutdownNow();
//        }
//        System.out.println("Scraping finished");
//
//    }
}
