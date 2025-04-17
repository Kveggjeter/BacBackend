package com.bac.bacbackend.controller;

import com.bac.bacbackend.data.repository.CacheController;
import com.bac.bacbackend.data.repository.NewSourceRepository;
import com.bac.bacbackend.data.repository.ScraperObjectRepository;
import com.bac.bacbackend.data.scraper.WebCrawler;
import com.bac.bacbackend.data.scraper.config.WebSetter;
import com.bac.bacbackend.data.model.Article;
import com.bac.bacbackend.data.scraper.Bot;
import com.bac.bacbackend.data.repository.ArticleRepository;
import com.bac.bacbackend.data.model.NewSource;
import com.bac.bacbackend.data.model.StringResource;
import com.bac.bacbackend.domain.service.OpenAi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {

    private final Bot bot;
    private final ScraperObjectRepository scrapRepo;
    private final NewSourceRepository nsRepo;
    private final ArticleRepository repo;
    private final OpenAi ai;
    private final WebCrawler webCrawler;
    private final CacheController cache;
    private final WebSetter ws;
    @Value("${ai.summary}")
    private String coSummary;
    private final String command = StringResource.COMMAND.getValue();
    protected static final String skyUrl = "https://www.skynews.com.au/australia-news";
    protected final String skyTxtLocator = "h4 a";
    protected final String skyTxtHref = "href";
    protected final String skyImgLocator = "div.responsive-img";
    protected final String skyImgHref = "innerHTML";
    protected final String skyTitle = "h1";
    protected final String skySum = "div.article-content";

    private Controller (Bot bot, ScraperObjectRepository scrapRepo, NewSourceRepository nsRepo, ArticleRepository repo, OpenAi ai, WebCrawler webCrawler, CacheController cache, WebSetter ws) {
        this.bot = bot;
        this.scrapRepo = scrapRepo;
        this.nsRepo = nsRepo;
        this.repo = repo;
        this.ai = ai;
        this.webCrawler = webCrawler;
        this.cache = cache;
        this.ws = ws;
    }

    @RequestMapping("/")
    public String index() { return "Application is running"; }

    @RequestMapping("/cache")
    public void ch () {
        try {
            cache.cache();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Bein til helvete som vanlig");
        }
    }

    @RequestMapping("/read")
    public String read() { return repo.findById("https://asianews.network/cutting-edge-features-help-chinas-homegrown-aircraft-fly-even-higher/").toString(); }

    @GetMapping("/country")
    public int country(@RequestParam String country) {
        int count = 0;
        Iterable<Article> articles = repo.findAll();
        for (Article article : articles) {
            if (article != null &&
                    article.getCountry() != null &&
                    article.getCountry().equalsIgnoreCase(country))
            {
                count++;
            }
        }
        return count;
    }

    @RequestMapping("/pg")
    public String pg() {
        return scrapRepo.findAll().toString();
    }

    @RequestMapping("/start")
    public String start() {
        int n = ws.getCount();
        bot.start(n, 2);
        return "Scraping started";
    }

    @RequestMapping("/ai")
    public String oai() {

        String[] res = ai.prompt(command, coSummary).split("/");
        String city = res[0], country = res[1], continent = res[2],
                region = res[3], category = res[4], x = res[5], y = res[6];
        System.out.println(Arrays.toString(res));
        System.out.println("City: " + city + " Country: " + country + " Continent: " + continent + "Region: " + region + " Category: " + category + " X: " + x + " Y: " + y);
        return "Ai started";
    }

    @RequestMapping ("/crawl")
    public String crawl() {
        webCrawler.startCrawling(ws.getCount(), 5);
        return "Crawling started";
    }


    @GetMapping("/news")
    public List<Article> getNews() {
     return (List<Article>) repo.findAll();
    }

    @RequestMapping("/rs")
    public List<NewSource> getRs() {
        return (List<NewSource>) nsRepo.findAll();
    }


//    @GetMapping("/single")
//    public String startSingle() {
//        bot.startSingle();
//        return "Single started";
//    }

}