package com.bac.bacbackend.application.routine;

import com.bac.bacbackend.application.routine.crawling.WebCrawler;
import com.bac.bacbackend.application.routine.scraping.Scraper;
import com.bac.bacbackend.domain.port.IChrome;
import com.bac.bacbackend.domain.service.crawling.Crawler;
import org.springframework.stereotype.Component;

/**
 * Final child class of {@link com.bac.bacbackend.application.threads.MultiThreading} and {@link Bot}.
 * Used for starting the chosen routine, This routine is for scraping a larger number of articles and
 * are mainly used as either a timed scheduled after a checker has controlled that the number of articles is below a
 * certain threshold or at the start of an application if the article bundle seems empty.
 */
@Component
public final class BigScrape extends Bot {

    /**
     * Mandatory constructor for declaring which element we still consider as super.
     *
     * @param scraper {@link Scraper}
     * @param crawler {@link Crawler}
     * @param browser {@link com.bac.bacbackend.data.repository.browser.Browser}
     */
    public BigScrape(Scraper scraper, WebCrawler crawler, IChrome browser) {
        super(scraper, crawler, browser);
    }

    /**
     * This method simply starts the process, calls the doStart() method from its parent class
     * {@link Bot}. Added some logging for easier error-handling.
     *
     * @param propertyIndex the amount of news-source we have to go through
     * @param maxArticles the max amount of articles to be scraped
     */
    public void start(int propertyIndex, int maxArticles) {
        System.out.println("Starting full scraping");
        doStart(propertyIndex, maxArticles);
        System.out.println("Full scraping complete.");
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
     * used for determine how long to sleep before next iteration.
     *
     * @return the number of milliseconds the task will wait before next iteration.
     */
    @Override
    protected int sleepTimeMilliseconds() {
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
        return 5;
    }
}
