package com.bac.bacbackend.controller;

import com.bac.bacbackend.domain.model.article.Article;
import com.bac.bacbackend.domain.port.IArticleRepo;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import com.bac.bacbackend.domain.port.IScraperObjectRepo;
import com.bac.bacbackend.domain.service.scraper.ScraperStrat;
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
    private final ScraperStrat st;
    private final IScraperObjectRepo sRepo;

    private Controller(WebCrawler wc, INewsParamRepo nRepo, IArticleRepo aRepo, ScraperStrat st, IScraperObjectRepo sRepo) {
        this.wc = wc;
        this.nRepo = nRepo;
        this.aRepo = aRepo;
        this.st = st;
        this.sRepo = sRepo;
    }

    @RequestMapping("/crawl")
    public String crawl() {
        wc.crawl(nRepo.getCount());
        return "Crawling started";
    }

    @RequestMapping("/start")
        public String scrape() {
            st.start("https://www.reuters.com/legal/trump-challenges-judges-probes-compliance-with-deportation-orders-2025-04-17/");
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

}
