//package com.bac.bacbackend.config;
//
//import com.bac.bacbackend.data.scraper.Bot;
//import com.bac.bacbackend.data.repository.scraper.NewsParamRepo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * Source: https://www.youtube.com/watch?v=YhZP6tSDXig&ab_channel=Devtiro
// */
//@Component
//@Slf4j
//public class TimedScraper {
//    private final NewsParamRepo ws;
//    private final Bot bot;
//
//    private TimedScraper (NewsParamRepo ws, Bot bot) {
//        this.ws = ws;
//        this.bot = bot;
//    }
//    /**
//     * Translater to save some time
//     * https://crontab.guru/
//     */
//    @Scheduled (cron = "0 0 0,6 * * *")
//    public void scrape() {
//        int n = ws.getCount();
//        bot.start(n, 5);
//    }
//}
