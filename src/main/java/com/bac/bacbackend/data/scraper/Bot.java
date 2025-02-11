package com.bac.bacbackend.data.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Bot {
    private final WebCrawler webCrawler;
    private final Scraper scraper;
    private static final String url = "https://www.reuters.com/world/";

    @Autowired
    private Bot(WebCrawler webCrawler, Scraper scraper) {
        this.webCrawler = webCrawler;
        this.scraper = scraper;
    }

    public void start () {
        List<WebCrawler.ArticleData> articles = webCrawler.startCrawling(10, url);
        System.out.println("Sending articles to scraper...");

        for (WebCrawler.ArticleData articleData : articles) {
            scraper.startScraping(articleData.articleUrl(), articleData.imgUrl());
        }
        System.out.println("Scraping finished.");
    }
}
