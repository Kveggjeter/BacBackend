package com.bac.bacbackend.controller;

import com.bac.bacbackend.domain.model.article.Article;
import com.bac.bacbackend.domain.port.IArticleRepo;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import com.bac.bacbackend.domain.port.IScraperObjectRepo;
import com.bac.bacbackend.domain.service.deepCrawler.Diver;
import com.bac.bacbackend.domain.service.routine.BotController;
import com.bac.bacbackend.domain.service.scraper.WebCrawler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {

    private final WebCrawler wc;
    private final INewsParamRepo nRepo;
    private final IArticleRepo aRepo;
    private final IScraperObjectRepo sRepo;
    private final BotController bot;
    private final Diver diver;

    private Controller(WebCrawler wc, INewsParamRepo nRepo, IArticleRepo aRepo, IScraperObjectRepo sRepo, BotController bot, Diver diver) {
        this.wc = wc;
        this.nRepo = nRepo;
        this.aRepo = aRepo;
        this.sRepo = sRepo;
        this.bot = bot;
        this.diver = diver;
    }

    @RequestMapping("/crawl")
    public String crawl() {
        wc.crawl(nRepo.getCount(), 10);
        return "Crawling started";
    }

    @RequestMapping("/start")
        public String scrape() {
            bot.start(nRepo.getCount(), 2);
            return "Skraping starta";
    }

    @RequestMapping("/cache")
    public String cache() {
        sRepo.cache();
        return "Cache started";
    }

    @RequestMapping("/news")
    public List<Article> getNews() {
        return aRepo.news();
    }

    @RequestMapping("/dive")
    public void dive() {
        diver.dive();
    }

}
