package com.bac.bacbackend.data.datasource.scraperUtils;

import java.util.List;

public class ScraperMain {
    private static final String url = "https://www.reuters.com/world/";

    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        List<String> latestArticles = crawler.startCrawling(18, url);

        System.out.println("\n 18 artikler hentet:");
        for (String url : latestArticles) {
            System.out.println(url);
        }
    }
}
