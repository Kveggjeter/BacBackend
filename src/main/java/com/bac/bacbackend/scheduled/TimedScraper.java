package com.bac.bacbackend.scheduled;

import com.bac.bacbackend.data.scraper.Bot;
import com.bac.bacbackend.data.scraper.StringBank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Source: https://www.youtube.com/watch?v=YhZP6tSDXig&ab_channel=Devtiro
 */
@Component
@Slf4j
public class TimedScraper {
    private final StringBank sb;
    private final Bot bot;

    private TimedScraper (StringBank sb, Bot bot) {
        this.sb = sb;
        this.bot = bot;
    }
    /**
     * Translater to save some time
     * https://crontab.guru/
     */
    @Scheduled (cron = "0 0 0,12 * * *")
    public void scrape() {
        int n = sb.getUrl().size() - 1;
        bot.start(n);
    }
}
