package com.bac.bacbackend.application.routine;

import com.bac.bacbackend.application.routine.crawling.WebCrawler;
import com.bac.bacbackend.application.routine.scraping.Scraper;
import org.springframework.stereotype.Component;

/**
 * This bot scans various websites and updates the db when one of them are updated.
 */
@Component
public class NewsPatrol extends Bot {

    public NewsPatrol(Scraper scraper, WebCrawler crawler) {
        super(scraper, crawler);
    }

    /**
     * Starting the application, this is connected to the automater.
     *
     * @param propertyIndex for choosing what webelement to crawl with
     */
    public void start(int propertyIndex) {
        System.out.println("Starting patrolling");
        doStart(propertyIndex, 1);
        System.out.println("Patrolling done");
    }

    @Override
    protected int waitTimeSeconds() {
        return 120;
    }

    @Override
    protected int sleepTimeMilliseconds() {
        int min = 2000;
        int max = 15000;
        int sleep = (int)(Math.random() * (max - min) + min);
        System.out.println("Sleeping for " + sleep + " ms. ");
        return sleep;
    }

    @Override
    protected int threadNumber() {
        return 1;
    }
}

