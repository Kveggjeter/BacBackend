package com.bac.bacbackend.application;

import com.bac.bacbackend.application.routine.BigScrape;
import com.bac.bacbackend.application.routine.NewsPatrol;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Source: https://www.youtube.com/watch?v=YhZP6tSDXig&ab_channel=Devtiro
 */
@Component
@Slf4j
public class TimedScraper {
    private final INewsParamRepo repo;
    private final BigScrape bigScrape;
    private final NewsPatrol newsPatrol;

    private TimedScraper (INewsParamRepo repo, BigScrape bigScrape, NewsPatrol newsPatrol) {
        this.repo = repo;
        this.bigScrape = bigScrape;
        this.newsPatrol = newsPatrol;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void spy() { newsPatrol.start(repo.sumOfAllSources()); }

    /**
     * Translater to save some time
     * https://crontab.guru/
     */
    @Scheduled (cron = "0 0 0,6 * * *")
    public void scrape() {
        bigScrape.start(repo.sumOfAllSources(), 5);
    }
}
