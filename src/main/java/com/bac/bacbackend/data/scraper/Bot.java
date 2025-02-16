package com.bac.bacbackend.data.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class Bot extends StringBank {
    private final WebCrawler webCrawler;
    private final Scraper scraper;
    private final int threadNumber = 5;
    private final int maxArticles = 5;

    @Autowired
    public Bot(WebCrawler webCrawler, Scraper scraper) {
        this.webCrawler = webCrawler;
        this.scraper = scraper;
    }

    public void startSingle() {

        List<WebCrawler.ArticleData> articles = webCrawler.startCrawling(
                maxArticles, anUrl, anTxtLocator, anTxtHref, anImgLocator, anImgHref
        );
        System.out.println("Sending articles to scraper...");
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);

        for (WebCrawler.ArticleData articleData : articles) {
            executorService.submit(() -> {
                scraper.scrape(articleData.articleUrl(), articleData.imgUrl(), anTitle, anSum);
            });
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(120, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        }
        catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        System.out.println("Scraping finished");

    }

    public void start(int n) {

        if (n < 0) {
            System.out.println("Webscraping has finished.");
            return;
        }

        ArrayList<String> url = getUrl();
        ArrayList<String> txtLocator = getTxtLocator();
        ArrayList<String> txtHref = getTxtHref();
        ArrayList<String> imgHref = getImgHref();
        ArrayList<String> imgLocator = getImgLocator();
        ArrayList<String> tit = getTitle();
        ArrayList<String> sum = getSummary();

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

        // TODO: Rotating IP
//        if (articles.isEmpty()) {
//            System.out.println("No articles returned, trying again..");
//            try {
//                Thread.sleep(5000);
//                for (WebCrawler.ArticleData articleData : articles) {
//                    executorService.submit(() -> {
//                        scraper.scrape(articleData.articleUrl(), articleData.imgUrl(), nisTitle, nisSum);
//                    });
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

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
}
