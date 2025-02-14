package com.bac.bacbackend.controller;

import com.bac.bacbackend.data.scraper.WebCrawler;
import com.bac.bacbackend.domain.model.Article;
import com.bac.bacbackend.data.scraper.Bot;
import com.bac.bacbackend.data.repository.ArticleRepository;
import com.bac.bacbackend.domain.model.StringResource;
import com.bac.bacbackend.domain.service.OpenAi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {

    private final Bot bot;
    private final ArticleRepository repo;
    private final OpenAi ai;
    private final WebCrawler webCrawler;
    @Value("${ai.summary}")
    private String coSummary;
    private final String command = StringResource.COMMAND.getValue();
    protected static final String anUrl = "https://asianews.network/category/home-page/daily-digest/";
    protected final String anTxtLocator = "div.post-img a";
    protected final String anTxtHref = "href";
    protected final String anImgLocator = anTxtLocator + " img";
    protected final String anImgHref = "src";
    protected final String anTitle = "h1";
    protected final String anSum = "div.article__summary";




    private Controller (Bot bot, ArticleRepository repo, OpenAi ai, WebCrawler webCrawler) {
        this.bot = bot;
        this.repo = repo;
        this.ai = ai;
        this.webCrawler = webCrawler;
    }

    @RequestMapping("/")
    public String index() { return "Application is running"; }

    @RequestMapping("/read")
    public String read() { return repo.findById("1").toString(); }

    @RequestMapping("/save")
    public String save() {
        Article a = new Article();
        a.setSourceName("a jerk");
        a.setId("www.yourmom.com");
        a.setTitle("nice");
        a.setImgUrl("www.nty.io");
        repo.save(a);
        return a.toString();
    }

    @RequestMapping("/start")
    public String start() {
        bot.start();
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
        webCrawler.startCrawling(10, anUrl, anTxtLocator, anTxtHref, anImgLocator, anImgHref);
        return "Crawling started";
    }


    @GetMapping("/news")
    public List<Article> getNews() {
     return (List<Article>) repo.findAll();
    }

    @GetMapping("/location")
    public List<LocationResponse> getLocations() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = Paths.get("src/main/java/com/bac/bacbackend/repository/Markers.JSON").toFile();

        return objectMapper.readValue(file, new TypeReference<>() {});
    }

    public static class LocationResponse {
        private double x;
        private double y;
        private String html;

        public LocationResponse() {}

        public double getX() { return x; }
        public void setX(double x) { this.x = x; }

        public double getY() { return y; }
        public void setY(double y) { this.y = y; }

        public String getHtml() { return html; }
        public void setHtml(String html) { this.html = html; }
    }
}