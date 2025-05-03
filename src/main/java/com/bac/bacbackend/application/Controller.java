package com.bac.bacbackend.application;

import com.bac.bacbackend.application.routine.BigScrape;
import com.bac.bacbackend.application.routine.NewsPatrol;
import com.bac.bacbackend.application.routine.crawling.NewsArticleCrawler;
import com.bac.bacbackend.application.routine.scraping.NewsArticleScraper;
import com.bac.bacbackend.domain.common.CheckCategoryByCountry;
import com.bac.bacbackend.domain.common.MostPopularValue;
import com.bac.bacbackend.domain.model.article.Article;
import com.bac.bacbackend.domain.model.article.ArticleFacts;
import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.port.IArticleRepo;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import com.bac.bacbackend.domain.port.IScraperObjectRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
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
    private final NewsArticleScraper newsArticleScraper;

    private Controller(NewsArticleCrawler newsArticleCrawler, NewsArticleScraper newsArticleScraper,  INewsParamRepo nRepo, IArticleRepo aRepo, IScraperObjectRepo sRepo, BigScrape bot, NewsPatrol newsPatrol) {
        this.newsArticleCrawler = newsArticleCrawler;
        this.newsArticleScraper = newsArticleScraper;
        this.nRepo = nRepo;
        this.aRepo = aRepo;
        this.sRepo = sRepo;
        this.bot = bot;
        this.newsPatrol = newsPatrol;
    }

    @RequestMapping("/start")
        public String scrape() {
            bot.start(nRepo.sumOfAllSources(), 30);
            return "Skraping starta";
    }

    @RequestMapping("/cache")
    public String cache() {
        sRepo.cache();
        return "Cache started";
    }

    @RequestMapping("/crawl")
    public List<ArticleUrls> crawl(){
        return newsArticleCrawler.crawlWebsites(5, nRepo.sumOfAllSources());

    }

    @RequestMapping("scrapeTest")
    public void scrapeTest() {
        ArticleUrls urls = new ArticleUrls("https://www.france24.com/en/americas/20250429-amazon-says-it-will-not-show-tariff-costs-goods-white-house-rebuke-trump", "<source type=\"image/webp\" srcset=\"https://s.france24.com/media/display/c4e545a2-251a-11f0-b02c-005056a90284/w:177/p:16x9/000_43WH3PT.webp 177w,https://s.france24.com/media/display/c4e545a2-251a-11f0-b02c-005056a90284/w:388/p:16x9/000_43WH3PT.webp 388w,https://s.france24.com/media/display/c4e545a2-251a-11f0-b02c-005056a90284/w:480/p:16x9/000_43WH3PT.webp 480w,https://s.france24.com/media/display/c4e545a2-251a-11f0-b02c-005056a90284/w:720/p:16x9/000_43WH3PT.webp 720w\" sizes=\"(max-width: 639px) calc(100vw - 32px), (max-width: 1076px) calc(50vw - 64px), 322px\">\n" +
                "        <img fetchpriority=\"low\" src=\"https://s.france24.com/media/display/c4e545a2-251a-11f0-b02c-005056a90284/w:720/p:16x9/000_43WH3PT.jpg\" alt=\"White House Press Secretary Karoline Leavitt holds a news article on Amazon CEO Jeff Bezos as she speaks to reporters in Washington DC, on April 29, 2025.\" srcset=\"https://s.france24.com/media/display/c4e545a2-251a-11f0-b02c-005056a90284/w:177/p:16x9/000_43WH3PT.jpg 177w,https://s.france24.com/media/display/c4e545a2-251a-11f0-b02c-005056a90284/w:388/p:16x9/000_43WH3PT.jpg 388w,https://s.france24.com/media/display/c4e545a2-251a-11f0-b02c-005056a90284/w:480/p:16x9/000_43WH3PT.jpg 480w,https://s.france24.com/media/display/c4e545a2-251a-11f0-b02c-005056a90284/w:720/p:16x9/000_43WH3PT.jpg 720w\" sizes=\"(max-width: 639px) calc(100vw - 32px), (max-width: 1076px) calc(50vw - 64px), 322px\" width=\"720\" height=\"405\" loading=\"lazy\" class=\"m-figure__img lazy\" data-ll-status=\"native\">");
        newsArticleScraper.scrapeWebsite(urls, nRepo.sumOfAllSources());
    }

    @RequestMapping("/country")
    public ArticleFacts country(@RequestParam String country) {
        Iterable<Article> articles = aRepo.news();
        return ArticleFacts.getArticleFacts(country, articles);
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
