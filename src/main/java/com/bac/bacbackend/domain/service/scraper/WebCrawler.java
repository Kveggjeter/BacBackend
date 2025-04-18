//package com.bac.bacbackend.domain.service.scraper;
//
//import com.bac.bacbackend.domain.model.scraper.ArticleData;
//import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
//import com.bac.bacbackend.domain.port.IArticleRepo;
//import com.bac.bacbackend.domain.port.IChrome;
//import com.bac.bacbackend.domain.port.INewsParamRepo;
//import com.bac.bacbackend.data.service.webdriver.Crawler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import java.util.ArrayList;
//import org.openqa.selenium.WebDriver;
//
//
//@Component
//public class WebCrawler {
//
//    @Autowired
//    private IArticleRepo aRepo;
//    private final INewsParamRepo nRepo;
//    private final IChrome browser;
//
//    public WebCrawler(IArticleRepo aRepo, INewsParamRepo nRepo, IChrome browser) {
//        this.aRepo = aRepo;
//        this.nRepo = nRepo;
//        this.browser = browser;
//    }
//
//    public ArrayList<ArticleData> startCrawling(int n, int maxNum) {
//
//        ArrayList<ArticleData> articlesList = new ArrayList<>();
//        ScrapeProps s = nRepo.select(n);
//        browser.start(s.url());
//
//        try {
//
//            System.out.println("Crawling webpage, please wait...");
//            crawl(0, driver, maxNum, s, articlesList);
//
//            int count = articlesList.size();
//            System.out.println("Crawling finished. Managed to get " + count + " amount of articles");
//
//        } finally {
//            browser.stop();
//        }
//        return articlesList;
//    }
//
//    private ArrayList<ArticleData> crawl(
//            int index,
//            WebDriver driver,
//            int maxNum,
//            ScrapeProps s,
//            ArrayList<ArticleData> list) {
//        if (list.size() >= maxNum) return list;
//
//        Crawler txt = new Crawler(driver);
//        Crawler img = new Crawler(driver);
//
//        try {
//            txt.setElement(s.txtLocator());
//            txt.content(index);
//            String url = txt.value(s.txtHref());
//
//            img.setElement(s.txtLocator());
//            img.content(index);
//            String imgUrl = img.value(s.imgHref());
//
//            if(url != null && !aRepo.exists(url)
//                    && !list.contains(new ArticleData(url, imgUrl))
//            ){
//                list.add(new ArticleData(url, imgUrl));
//                System.out.println("Fetched article #" + list.size() + ": " + url);
//                System.out.println("Fetched img #" + list.size() + ": " + imgUrl);
//            }
//
//            return crawl(index + 1, driver, maxNum, s, list);
//
//        } catch (Exception e) {
//            System.out.println("Error while fetching articles: " + e.getMessage());
//        }
//        return list;
//    }
//
//
//}
//
//
//
