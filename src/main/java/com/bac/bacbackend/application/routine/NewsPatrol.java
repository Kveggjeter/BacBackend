package com.bac.bacbackend.application.routine;

import com.bac.bacbackend.application.routine.crawling.WebCrawler;
import com.bac.bacbackend.application.routine.scraping.Scraper;
import com.bac.bacbackend.domain.port.IBrowser;
import com.bac.bacbackend.domain.service.crawling.Crawler;
import org.springframework.stereotype.Component;

/**
 * This bot scans various websites and updates the db when one of them are updated.
 * Final child class of {@link com.bac.bacbackend.application.threads.MultiThreading} and {@link Bot}.
 * Runs frequently as long as the application is running.
 */
@Component
public final class NewsPatrol extends Bot {

    /**
     * Mandatory constructor for declaring which element we still consider as super.
     *
     * @param scraper {@link Scraper}
     * @param crawler {@link Crawler}
     * @param browser {@link com.bac.bacbackend.data.repository.browser.Browser}
     */
    public NewsPatrol(Scraper scraper, WebCrawler crawler, IBrowser browser) {
        super(scraper, crawler, browser);
    }

    /**
     * Starting the application, this is connected to the automation.
     *
     * @param propertyIndex for choosing what webelement to crawl with
     */
    public void start(int propertyIndex) {
        System.out.println("Starting patrolling");
        doStart(propertyIndex, 1);
        System.out.println("Patrolling done");
    }

    /**
     * Inherited method by parentclass {@link com.bac.bacbackend.application.threads.MultiThreading}
     * used for determing how long before a thread will time out.
     *
     * @return the number of seconds the threads are to wait before terminating
     */
    @Override
    protected int waitTimeSeconds() {
        return 120;
    }

    /**
     * Inherited method by parentclass {@link com.bac.bacbackend.application.threads.MultiThreading}
     * used for determine how long to sleep before next iteration. Sleeps at a random rate to mitigate detection
     * from website protection. 2-45 seconds have worked great for some time, but to change you can simply
     * change the min and max value.
     *
     * @return the number of milliseconds the task will wait before next iteration.
     */
    @Override
    protected int sleepTimeMilliseconds() {
//        int min = 2000;
//        int max = 45000;
//        int sleep = (int)(Math.random() * (max - min) + min);
//        System.out.println("Sleeping for " + sleep + " ms. ");
//        return sleep;
        return 0;
    }

    /**
     * Inherited method by parentclass {@link com.bac.bacbackend.application.threads.MultiThreading}
     * used for determine how many threads to run the scraping on.
     *
     * @return the number threads to run the scraper on.
     */
    @Override
    protected int threadNumber() {
        return 1;
    }
}

