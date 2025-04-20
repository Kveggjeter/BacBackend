//package com.bac.bacbackend.controller;
//
//import com.bac.bacbackend.domain.model.article.Article;
//import com.bac.bacbackend.domain.port.IArticleRepo;
//import com.bac.bacbackend.domain.port.INewsParamRepo;
//import com.bac.bacbackend.domain.port.IScraperObjectRepo;
//import com.bac.bacbackend.domain.service.routine.BotController;
//import com.bac.bacbackend.domain.service.scraper.TestScrape;
//import com.bac.bacbackend.domain.service.scraper.WebCrawler;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@CrossOrigin(origins = "http://localhost:5173")
//@RestController
//public class TestController {
//    private final WebCrawler wc;
//    private final INewsParamRepo nRepo;
//    private final IArticleRepo aRepo;
//    private final IScraperObjectRepo sRepo;
//    private final BotController bot;
//    private final TestScrape ts;
//
//    private TestController(WebCrawler wc, INewsParamRepo nRepo, IArticleRepo aRepo, IScraperObjectRepo sRepo, BotController bot, TestScrape ts) {
//        this.wc = wc;
//        this.nRepo = nRepo;
//        this.aRepo = aRepo;
//        this.sRepo = sRepo;
//        this.bot = bot;
//        this.ts = ts;
//    }
//
//
//    @RequestMapping("/crawl")
//    public String crawl() {
//        wc.crawl(nRepo.getCount(), 5);
//        return "Crawling started";
//    }
//
//    @RequestMapping("/TestSkrap")
//    public String scrape() {
//        ts.start();
//        return "Skraping starta";
//    }
//
//    @RequestMapping("/news")
//    public List<Article> getNews() {
//        return aRepo.news();
//    }
//
//
//
//}