package com.bac.bacbackend.data.datasource.scraperUtils;

import java.util.List;

public class ScraperMain {

    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        List<String> latestArticles = crawler.startCrawling(18);

        System.out.println("\n 18 artikler hentet:");
        for (String url : latestArticles) {
            System.out.println(url);
        }
    }
}
