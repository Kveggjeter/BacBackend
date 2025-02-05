package com.bac.bacbackend.data.datasource.scraperUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class WebCrawler {
    private final List<String> articleUrls = new ArrayList<>();
    private final String container = "h3 a";
    private final String href = "href";

    public List<String> startCrawling(int maxArticles, String url) {

            BrowserSettings browserSettings = new BrowserSettings();
            WebDriver driver = browserSettings.driver();

            driver.get(url);
            List<String> articles = crawl(driver, 0, maxArticles);
            driver.quit();

            return articles;

    }

    private List<String> crawl(WebDriver driver, int index, int maxArticles) {
        if (articleUrls.size() >= maxArticles) {
            return articleUrls;
        }

        try {
            List<WebElement> articles = driver.findElements(By.cssSelector(container));

            if (index < articles.size()) {
                String articleUrl = articles.get(index).getAttribute(href);

                    if (!articleUrls.contains(articleUrl)) {
                        articleUrls.add(articleUrl);
                        System.out.println("Hentet artikkel #" + articleUrls.size() + ": " + articleUrl);
                    }
                return crawl(driver, index + 1, maxArticles);
            }

        } catch (Exception e) {
            System.out.println("Feil ved scraping: " + e.getMessage());
        }
        return articleUrls;
    }
}





