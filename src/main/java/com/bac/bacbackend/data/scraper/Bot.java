package com.bac.bacbackend.data.scraper;

import com.bac.bacbackend.data.scraper.config.WebSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The websetter can be abstracted yet another level, by making new list of each instances so we dont need to
 * iterate and choose index for each array. It matches some-what magically now because we've been adding it that way
 * in memory throughout, but it's pretty fragile.
 * TODO: Reduce the immense paramterization going on, maybe even rewrite the whole thing
 */
@Component
public class Bot {
    private final WebCrawler webCrawler;
    private final WebSetter webSetter;
    private final Scraper scraper;
    private final int threadNumber = 5;
    private final int maxArticles = 1;

    @Autowired
    public Bot(WebCrawler webCrawler, Scraper scraper, WebSetter webSetter) {
        this.webCrawler = webCrawler;
        this.scraper = scraper;
        this.webSetter = webSetter;
    }

    public void start(int n) {

        if (n < 0) {
            System.out.println("Webscraping has finished.");
            return;
        }

        List<String> url = webSetter.getUrl();
        List<String> txtLocator = webSetter.getTxtLocator();
        List<String> txtHref = webSetter.getTxtHref();
        List<String> imgHref = webSetter.getImgHref();
        List<String> imgLocator = webSetter.getImgLocator();
        List<String> tit = webSetter.getTitle();
        List<String> sum = webSetter.getSum();

        List<WebCrawler.ArticleData> articles = webCrawler.startCrawling(
                maxArticles, url.get(n), txtLocator.get(n), txtHref.get(n), imgLocator.get(n), imgHref.get(n)
        );
        System.out.println("Sending articles to scraper...");
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);

        for (WebCrawler.ArticleData articleData : articles) {
            executorService.submit(() -> {
                scraper.scrape(articleData.articleUrl(), articleData.imgUrl(), tit.get(n), sum.get(n));
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
            start(n - 1);
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
