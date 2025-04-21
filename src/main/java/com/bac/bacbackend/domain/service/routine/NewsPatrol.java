package com.bac.bacbackend.domain.service.routine;

import com.bac.bacbackend.domain.service.scraper.Scraper;
import com.bac.bacbackend.domain.service.scraper.WebCrawler;
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

    /**
     * Override from parent class, setting a sleep time in milliseconds.
     * The time is set between 2 and 15 seconds, to throw of Cloudflare and other
     * bummer firewalls/protectors/etc..
     *
     * @return sleep-time
     */
    @Override
    protected int sleepTimeMilliseconds() {
        int min = 2000;
        int max = 15000;
        int sleep = (int)(Math.random() * (max - min) + min);
        System.out.println("Sleeping for " + sleep + " ms. ");
        return sleep;
    }

    /**
     * Override from parent class. We only run thread at the time for the patrol. Speed is not
     * an issue here.
     *
     * @return number of threads
     */
    @Override
    protected int threadNumber() {
        return 1;
    }

    /**
     * Waits 120 second before terminating the thread. 120 seconds is mightily long for this
     * use-case, so we might even bump it down.
     *
     * @return time before thread is terminated
     */
    @Override
    protected int waitTimeSeconds() {
        return 120;
    }
}
