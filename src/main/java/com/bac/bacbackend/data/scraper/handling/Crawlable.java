package com.bac.bacbackend.data.scraper.handling;

import com.bac.bacbackend.data.scraper.WebCrawler;
import org.openqa.selenium.WebDriver;
import java.util.List;

public interface Crawlable {
    List<WebCrawler.ArticleData> crawl(WebDriver driver, ScrapeSource source, int maxNum);
}
