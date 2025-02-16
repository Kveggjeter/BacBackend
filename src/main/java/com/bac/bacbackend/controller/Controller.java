package com.bac.bacbackend.controller;

import com.bac.bacbackend.data.scraper.StringBank;
import com.bac.bacbackend.data.scraper.WebCrawler;
import com.bac.bacbackend.domain.model.Article;
import com.bac.bacbackend.data.scraper.Bot;
import com.bac.bacbackend.data.repository.ArticleRepository;
import com.bac.bacbackend.domain.model.StringResource;
import com.bac.bacbackend.domain.service.OpenAi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {

    private final Bot bot;
    private final ArticleRepository repo;
    private final StringBank sb;
    private final OpenAi ai;
    private final WebCrawler webCrawler;
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

    private Controller (Bot bot, ArticleRepository repo, OpenAi ai, WebCrawler webCrawler, StringBank sb) {
        this.bot = bot;
        this.repo = repo;
        this.ai = ai;
        this.webCrawler = webCrawler;
        this.sb = sb;
    }

    @RequestMapping("/")
    public String index() { return "Application is running"; }

    @RequestMapping("/read")
    public String read() { return repo.findById("1").toString(); }

    @RequestMapping("/save")
    public String save() {
        Article a = new Article();
        a.setSourceName("a jerk");
        a.setId("www.yourmomlllll.com");
        a.setTitle("nice");
        a.setImgUrl("www.nty.io");
        repo.save(a);
        return a.toString();
    }

    @RequestMapping("/start")
    public String start() {
        int n = sb.getUrl().size() - 1;
        bot.start(n);
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
        webCrawler.startCrawling(5, skyUrl, skyTxtLocator, skyTxtHref, skyImgLocator, skyImgHref);
        return "Crawling started";
    }


    @GetMapping("/news")
    public List<Article> getNews() {
     return (List<Article>) repo.findAll();
    }

    @GetMapping("/single")
    public String startSingle() {
        bot.startSingle();
        return "Single started";
    }

}