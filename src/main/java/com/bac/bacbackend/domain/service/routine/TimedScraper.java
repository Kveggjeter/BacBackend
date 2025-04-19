package com.bac.bacbackend.domain.service.routine;

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
    private final BotController bot;

    private TimedScraper (INewsParamRepo repo, BotController bot) {
        this.repo = repo;
        this.bot = bot;
    }
    /**
     * Translater to save some time
     * https://crontab.guru/
     */
    @Scheduled (cron = "0 0 0,6 * * *")
    public void scrape() {
        bot.start(repo.getCount(), 5);
    }
}
