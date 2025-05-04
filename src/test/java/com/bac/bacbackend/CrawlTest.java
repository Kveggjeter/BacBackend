//package com.bac.bacbackend;
//
//import com.bac.bacbackend.application.routine.crawling.NewsArticleCrawler;
//import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class CrawlTest {
//
//    private final NewsArticleCrawler test;
//
//    public CrawlTest(NewsArticleCrawler test) {
//        this.test = test;
//    }
//
//    @Test
//    public void crawlTest() {
//        ScrapingProperties scrapingProperties = new ScrapingProperties(
//                "https://www.france24.com/en/",
//                ".article__title a",
//                "href",
//                ".m-item-list-article__wrapper picture",
//                "innerHTML",
//                "",
//                ""
//        );
//        test.crawlWebsites(3, scrapingProperties);
//    }
//}
