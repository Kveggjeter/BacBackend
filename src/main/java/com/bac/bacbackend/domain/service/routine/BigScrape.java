package com.bac.bacbackend.domain.service.routine;

import com.bac.bacbackend.domain.service.scraper.Scraper;
import com.bac.bacbackend.domain.service.scraper.WebCrawler;
import org.springframework.stereotype.Component;

@Component
public final class BigScrape extends Bot {

    public BigScrape(Scraper scraper, WebCrawler crawler) {
        super(scraper, crawler);
    }

    public void start(int n, int maxNum) {
        System.out.println("Starting full scraping");
        doStart(n, maxNum);
        System.out.println("Full scraping complete.");
    }

    @Override
    protected int sleepTimeMilliseconds() {
        return 0;
    }

    @Override
    protected int threadNumber() {
        return 3;
    }

    @Override
    protected int waitTimeSeconds() {
        return 120;
    }
}
