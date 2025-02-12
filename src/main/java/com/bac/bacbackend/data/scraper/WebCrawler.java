package com.bac.bacbackend.data.scraper;

import com.bac.bacbackend.data.repository.ArticleRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebCrawler {

    @Autowired
    private ArticleRepository aRepo;

    public List<ArticleData> startCrawling(int maxArticles, String url, String txtLocator, String txtHref, String imgLocator, String imgHref) {
        List<ArticleData> articlesList = new ArrayList<>();
        BrowserSettings browserSettings = new BrowserSettings();
        WebDriver driver = browserSettings.driver();
        driver.get(url);
        List<ArticleData> articles = crawl(driver, 0, maxArticles, articlesList, txtLocator, txtHref, imgLocator, imgHref);
        driver.quit();
        return articles;
    }

    private List<ArticleData> crawl(WebDriver driver, int index, int maxArticles, List<ArticleData> articlesList, String txtLocator,
                                    String txtHref, String imgLocator, String imgHref
    ){
        if (articlesList.size() >= maxArticles) {
            return articlesList;
        }

        try {
            List<WebElement> articles = driver.findElements(By.cssSelector(txtLocator));
            List<WebElement> imgs = driver.findElements(By.cssSelector(imgLocator));

            if (index < articles.size()) {
                String articleUrl = articles.get(index).getAttribute(txtHref);
                String imgUrl = (index < imgs.size()) ? imgs.get(index).getAttribute(imgHref) : "NO_IMAGE";
                assert articleUrl != null;
                if (!aRepo.existsById(articleUrl)) {
                    if (!articlesList.contains(new ArticleData(articleUrl, imgUrl))) {
                        articlesList.add(new ArticleData(articleUrl, imgUrl));
                        System.out.println("Fetched article #" + articlesList.size() + ": " + articleUrl);
                        System.out.println("Fetched img #" + articlesList.size() + ": " + imgUrl);
                    }
                }
                return crawl(driver, index + 1, maxArticles, articlesList, txtLocator, txtHref, imgLocator, imgHref);
            }
        } catch (Exception e) {
            System.out.println("Error while fetching articles: " + e.getMessage());
        }
        return articlesList;
    }

    public record ArticleData(String articleUrl, String imgUrl) {}
}