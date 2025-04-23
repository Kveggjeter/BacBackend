package com.bac.bacbackend.application.routine;

import com.bac.bacbackend.application.routine.crawling.WebCrawler;
import com.bac.bacbackend.application.routine.scraping.Scraper;
import org.springframework.stereotype.Component;

@Component
public final class BigScrape extends Bot {

    public BigScrape(Scraper scraper, WebCrawler crawler) {
        super(scraper, crawler);
    }

    public void start(int propertyIndex, int maxArticles) {
        System.out.println("Starting full scraping");
        doStart(propertyIndex, maxArticles);
        System.out.println("Full scraping complete.");
    }

    @Override
    protected int waitTimeSeconds() {
        return 120;
    }

    @Override
    protected int sleepTimeMilliseconds() {
        return 0;
    }

    @Override
    protected int threadNumber() {
        return 3;
    }
}
