//package com.bac.bacbackend;
//
//import com.bac.bacbackend.application.routine.scraping.NewsArticleScraper;
//import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
//import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
//import com.bac.bacbackend.domain.service.crawling.ArticleUrlStrategy;
//import com.bac.bacbackend.domain.service.crawling.Crawler;
//import com.bac.bacbackend.domain.service.crawling.CrawlingStrategy;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@SpringBootTest
//public class ScrapeTest {
//
//    private NewsArticleScraper test;
//
//    @Test
//    public void scrapeTest() {
//        ArticleUrls urls = new ArticleUrls("", "");
//        ScrapingProperties scrapingProperties = new ScrapingProperties("",
//                "",
//                "",
//                "",
//                "",
//                "",
//                ""
//        );
//        test.scrapeWebsite(urls, scrapingProperties);
//    }
//    public List<ArticleUrls> crawlWebsites(int maxUrlsToCrawl, int propertyIndex) {
//        ScrapingProperties scrapingProperties = new ScrapingProperties(
//                "https://thediplomat.com/regions/central-asia/",
//                ".td-post",
//                "href",
//                ".td-post",
//                "innerHTML",
//                "h1",
//                "div.entry-content"
//        );
//        CrawlingStrategy<ArticleUrls> crawlingStrategy = new ArticleUrlStrategy(webSelectors, articleUrlValidator);
//        Crawler<ArticleUrls> crawler = new Crawler<>(browser, crawlingStrategy);
//
//        return crawler.crawl(scrapingProperties, maxUrlsToCrawl);
//    }
//}


