package com.bac.bacbackend.data.scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebCrawler {
    private final List<ArticleData> articlesList = new ArrayList<>();
    private final String container = "h3 a";
    private final String href = "href";
    private final String imgContainer = "div[data-testid='Image']";
    private final String img = "innerHTML";

    public List<ArticleData> startCrawling(int maxArticles, String url) {
        BrowserSettings browserSettings = new BrowserSettings();
        WebDriver driver = browserSettings.driver();
        driver.get(url);
        List<ArticleData> articles = crawl(driver, 0, maxArticles);
        driver.quit();
        return articles;
    }

    private List<ArticleData> crawl(WebDriver driver, int index, int maxArticles) {
        if (articlesList.size() >= maxArticles) {
            return articlesList;
        }

        try {
            List<WebElement> articles = driver.findElements(By.cssSelector(container));
            List<WebElement> imgs = driver.findElements(By.cssSelector(imgContainer));

            if (index < articles.size()) {
                String articleUrl = articles.get(index).getAttribute(href);
                String imgUrl = (index < imgs.size()) ? imgs.get(index).getAttribute(img) : "NO_IMAGE";

                if (!articlesList.contains(new ArticleData(articleUrl, imgUrl))) {
                    articlesList.add(new ArticleData(articleUrl, imgUrl));
                    System.out.println("Fetched article #" + articlesList.size() + ": " + articleUrl);
                    System.out.println("Fetched img #" + articlesList.size() + ": " + imgUrl);
                }
                return crawl(driver, index + 1, maxArticles);
            }
        } catch (Exception e) {
            System.out.println("Error while fetching articles: " + e.getMessage());
        }
        return articlesList;
    }

    public record ArticleData(String articleUrl, String imgUrl) {}
}