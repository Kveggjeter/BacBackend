//package com.bac.bacbackend.data.scraper;
//
//import com.bac.bacbackend.data.repository.scraper.NewsParamRepo;
//import com.bac.bacbackend.domain.model.scraper.ArticleData;
//import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
//import com.bac.bacbackend.domain.service.scraper.IStart;
//import com.bac.bacbackend.domain.service.scraper.Scraper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import java.util.ArrayList;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
///**
// * The websetter can be abstracted yet another level, by making new list of each instances so we dont need to
// * iterate and choose index for each array. It matches some-what magically now because we've been adding it that way
// * in memory throughout, but it's pretty fragile.
// * TODO: Reduce the immense parameterization going on, maybe even rewrite the whole thing
// */
//@Component
//public class Bot {
//    private final IStart webCrawler;
//    private final NewsParamRepo wbs;
//    private final Scraper scraper;
//
//    @Autowired
//    public Bot(IStart webCrawler, Scraper scraper, NewsParamRepo wbs) {
//        this.webCrawler = webCrawler;
//        this.scraper = scraper;
//        this.wbs = wbs;
//    }
//
//    public void start(int n, int maxNum) {
//
//        if (n < 0) {
//            System.out.println("Webscraping has finished.");
//            return;
//        }
//        ExecutorService pool = Executors.newFixedThreadPool(2);
//        ScrapeProps selectors = wbs.select(n);
//        ArrayList<ArticleData> articles = webCrawler.crawl(n);
//        System.out.println("Sending articles to scraper...");
//
//        for (ArticleData a : articles) {
//            pool.submit(() -> scraper.scrape(a.articleUrl(), a.imgUrl(), selectors));
//        }
//
//        pool.shutdown();
//        try {
//            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
//                pool.shutdownNow();
//            }
//        }
//        catch (InterruptedException e) {
//            pool.shutdownNow();
//        }
//            System.out.println("Scraping finished for index: " + n);
//            start(n - 1, maxNum);
//    }
//
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
// }
