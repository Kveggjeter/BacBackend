/**

package com.bac.bacbackend.controller;

import com.bac.bacbackend.domain.port.IScraperObjectRepo;
import com.bac.bacbackend.data.repository.scraper.NewsParamDataRepo;
import com.bac.bacbackend.data.repository.automation.ScraperDataObjectRepo;
import com.bac.bacbackend.domain.service.scraper.WebCrawler;
import com.bac.bacbackend.data.repository.scraper.NewsParamRepo;
//import com.bac.bacbackend.data.scraper.Bot;
import com.bac.bacbackend.data.repository.article.ArticleDataRepo;
import com.bac.bacbackend.domain.service.scraper.StringResource;
import com.bac.bacbackend.data.service.decomp.OpenAi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {

//    private final Bot bot;
    private final ScraperDataObjectRepo scrapRepo;
    private final NewsParamDataRepo nsRepo;
    private final ArticleDataRepo repo;
    private final OpenAi ai;
    private final IScraperObjectRepo cache;
    private final NewsParamRepo ws;
    private final WebCrawler crawler;
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

    private Controller (ScraperDataObjectRepo scrapRepo, NewsParamDataRepo nsRepo, ArticleDataRepo repo, OpenAi ai, IScraperObjectRepo cache, NewsParamRepo ws, WebCrawler crawler) {
        // this.bot = bot;
        this.scrapRepo = scrapRepo;
        this.nsRepo = nsRepo;
        this.repo = repo;
        this.ai = ai;
        this.cache = cache;
        this.ws = ws;
        this.crawler = crawler;
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

//    @GetMapping("/country")
//    public int country(@RequestParam String country) {
//        int count = 0;
//        Iterable<Article> articles = repo.findAll();
//        for (Article article : articles) {
//            if (article != null &&
//                    article.getCountry() != null &&
//                    article.getCountry().equalsIgnoreCase(country))
//            {
//                count++;
//            }
//        }
//        return count;
//    }

    @RequestMapping("/pg")
    public String pg() {
        return scrapRepo.findAll().toString();
    }

    @RequestMapping("/start")
    public String start() {
        int n = ws.getCount();
        // bot.start(n, 1);
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
        crawler.crawl(ws.getCount());
        return "Crawling started";
    }



//    @GetMapping("/single")
//    public String startSingle() {
//        bot.startSingle();
//        return "Single started";
//    }

 */