package com.bac.bacbackend.domain.service.routine;

import com.bac.bacbackend.domain.service.scraper.Scraper;
import com.bac.bacbackend.domain.service.scraper.WebCrawler;
import org.springframework.stereotype.Component;

@Component
public class Spy extends Bot {

    public Spy(Scraper scraper, WebCrawler crawler) {
        super(scraper, crawler);
    }

    public void start(int n) {
        System.out.println("Starting patrolling");
        doStart(n, 1);
        System.out.println("Patrolling done");
    }

    @Override
    protected int sleepTime() {
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

    @Override
    protected int waitTime() {
        return 120;
    }




}
