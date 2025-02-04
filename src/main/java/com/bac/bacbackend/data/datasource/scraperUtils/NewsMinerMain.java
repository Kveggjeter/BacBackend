package com.bac.bacbackend.data.datasource.scraperUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsMinerMain {
private static final int threadCount = 10;
    /**
     * TODO: Rydde opp i den driten her
     * @param args
     */
    public static void main(String[] args) {
        WebCrawler webCrawler = new WebCrawler();
        List<String> articles = webCrawler.startCrawling(10);

        System.out.println("Sending articles to scraper");
        startScraping(articles, threadCount);
        System.out.println("Scraping fullført.");
    }


    private static void startScraping(List<String> articles, int threadCount) {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (String url : articles) {
            executor.submit(new Scraper(url));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {

        }
    }
}


