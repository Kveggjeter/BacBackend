//package com.bac.bacbackend;
//
//import com.bac.bacbackend.application.routine.crawling.NewsArticleCrawler;
//import com.bac.bacbackend.domain.model.article.ScrapeContext;
//import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
//import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
//import com.bac.bacbackend.domain.service.crawling.ArticleUrlStrategy;
//import com.bac.bacbackend.domain.service.crawling.Crawler;
//import com.bac.bacbackend.domain.service.crawling.CrawlingStrategy;
//import com.bac.bacbackend.domain.service.scraping.ArticleScrapingStrategy;
//import com.bac.bacbackend.domain.service.scraping.Scraping;
//import com.bac.bacbackend.domain.service.scraping.ScrapingStrategy;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
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
//
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
