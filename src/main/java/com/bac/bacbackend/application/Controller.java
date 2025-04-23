package com.bac.bacbackend.application;

import com.bac.bacbackend.application.routine.BigScrape;
import com.bac.bacbackend.application.routine.NewsPatrol;
import com.bac.bacbackend.application.routine.crawling.NewsArticleCrawler;
import com.bac.bacbackend.domain.model.article.Article;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.port.IArticleRepo;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import com.bac.bacbackend.domain.port.IScraperObjectRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {

    private final INewsParamRepo nRepo;
    private final IArticleRepo aRepo;
    private final IScraperObjectRepo sRepo;
    private final BigScrape bot;
    private final NewsPatrol newsPatrol;
    private final NewsArticleCrawler newsArticleCrawler;

    private Controller(NewsArticleCrawler newsArticleCrawler,  INewsParamRepo nRepo, IArticleRepo aRepo, IScraperObjectRepo sRepo, BigScrape bot, NewsPatrol newsPatrol) {
        this.newsArticleCrawler = newsArticleCrawler;
        this.nRepo = nRepo;
        this.aRepo = aRepo;
        this.sRepo = sRepo;
        this.bot = bot;
        this.newsPatrol = newsPatrol;
    }

    @RequestMapping("/start")
        public String scrape() {
            bot.start(nRepo.sumOfAllSources(), 50);
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

    @RequestMapping("/spy")
    public void spyBot() {
        newsPatrol.start(nRepo.sumOfAllSources());
    }

    @RequestMapping("/testCrawl")
        public List<ArticleUrls> testCrawl() {
            return newsArticleCrawler.crawlWebsites(2, nRepo.sumOfAllSources());
        }
}
