//package com.bac.bacbackend.domain.service.scraper;
//
//import com.bac.bacbackend.domain.common.Regex;
//import com.bac.bacbackend.domain.model.article.Article;
//import com.bac.bacbackend.domain.port.*;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TestScrape {
//
//    private final IChrome browser;
//    private final ICrawler cr;
//    private final IOpenAi ai;
//    private final String command = StringResource.COMMAND.getValue();
//    private final Regex regex;
//    private final IArticleRepo repo;
//
//    private String url;
//    private String imgUrl;
//    private String title;
//    private String summary;
//    private String city;
//    private String country;
//    private String continent;
//    private String category;
//    private String x;
//    private String y;
//    private String sourceName;
//
//    private String startUrl = "https://www.aljazeera.com/africa/";
//    private String titleElement = "h1";
//    private String summaryElement = "p.article__subhead";
//    private String txtLocator = "section#news-feed-container h3 a";
//    private String txtHref = "href";
//    private String imgLocator = ".responsive-image";
//    private String imgHref = "src";
//
//    public TestScrape(IArticleRepo repo, IChrome browser, ICrawler cr, IOpenAi ai, Regex regex) {
//        this.repo = repo;
//        this.browser = browser;
//        this.cr = cr;
//        this.ai = ai;
//        this.regex = regex;
//    }
//
//    private Article toArticle() {
//        return new Article(
//                url, sourceName, title, summary,
//                city, country, continent,
//                category, x, y, imgUrl
//        );
//    }
//
//    private void save() {
//        try {
//            repo.adder(toArticle());
//        } catch (Exception e) {
//            throw new RuntimeException("Unexpected issue: " + e.getMessage(), e);
//        }
//    }
//
//    private void execute() {
//        if(crawl()) {
//            if (doScrape())
//                save();
//            else System.out.println("Scrape failed");
//        }
//        else System.out.println("Crawling failed");
//    }
//
//    public void start() {
//        execute();
//    }
//
//    public boolean crawl() {
//        browser.start(startUrl);
//        try {
//            url = cr.values(txtHref, txtLocator);
//            System.out.println(url);
//            imgUrl = cr.values(imgHref, imgLocator);
//            System.out.println(imgUrl);
//        } catch (RuntimeException e) {
//            return false;
//        } finally {
//            browser.stop();
//        }
//        return true;
//    }
//
//    private boolean doScrape() {
//
//        try {
//
//            browser.start(url);
//
//            title = cr.txtValue(titleElement);
//            System.out.println("Title: " + title);
//            summary = cr.txtValue(summaryElement);
//            System.out.println("Summary: " + summary);
//            summary = sumHandler(summary);
//
//            String[] res = ai.prompt(command, title + " " + summary).split("/");
//            if (res.length == 6) {
//                city = (res[0]);
//                System.out.println("City: " + city);
//                country = (res[1]);
//                System.out.println("Country: " + country);
//                continent = (res[2]);
//                System.out.println("Continent: " + continent);
//                category = (res[3]);
//                System.out.println("Category: " + category);
//                x = (res[4]);
//                System.out.println("X: " + x);
//                y = (res[5]);
//                System.out.println("Y: " + y);
//            }
//            else {
//                System.err.println("Prompt failed, AI returned " + res.length + " variables instead of prompted 6");
//                return false;
//            }
//
//            sourceName = regex.urlName(url);
//            System.out.println("Source Name: " + sourceName);
//
//            if (imgUrl != null) {
//                imgUrl = regex.imageSrc(imgUrl);
//            } else {
//                imgUrl = (cr.redo("img") != null ? regex.imageSrc(cr.redo("img")) : "NO_IMAGE");
//            }
//            System.out.println("Image URL: " + imgUrl);
//
//
//        } catch (Exception e) {
//            System.err.println("Failed to extract info from: " + imgUrl + ": " + e.getMessage());
//            throw e;
//        } finally {
//            try {
//                browser.stop();
//            } catch (Exception e) {
//                System.err.println(" Failed to close browser: " + e.getMessage());
//            }
//        }
//        return true;
//    }
//
//
//    private String sumHandler(String s) {
//        if (s == null) {
//            s = cr.txtValue("p");
//            if (s == null) {
//                System.err.println("No summary found");
//            }
//        }  if (s.length() > 400) s = s.substring(0, 400) + "...";
//        return s;
//    }
//}
