package com.bac.bacbackend.application.scheduled;

import com.bac.bacbackend.application.routine.BigScrape;
import com.bac.bacbackend.application.routine.NewsPatrol;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class of scheduled tasks that happens as long as the application is active. Using Cron to pick the time,
 * depending on what IDE you use (IntelliJ translates it for you) you might need this to put a new scheduled task down:
 * <p>
 * Translator to save some time
 * https://crontab.guru/
 */
@Component
@Slf4j
public class TimedScraper {
    private final INewsParamRepo repo;
    private final BigScrape bigScrape;
    private final NewsPatrol newsPatrol;

    public TimedScraper(INewsParamRepo repo, BigScrape bigScrape, NewsPatrol patrol) {
        this.repo = repo;
        this.bigScrape = bigScrape;
        this.newsPatrol = patrol;
    }

    /**
     * Running the spy-bot every five minutes to ensure we always have updated news
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void spy() { newsPatrol.start(repo.sumOfAllSources()); }

    /**
     * Running a bigger scrape every six hours. Might reduce this and add a check.
     */
    public void scrape() {
        bigScrape.start(repo.sumOfAllSources(), 5);
    }
}
