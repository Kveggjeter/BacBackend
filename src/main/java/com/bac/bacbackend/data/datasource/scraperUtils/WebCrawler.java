package com.bac.bacbackend.data.datasource.scraperUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.ArrayList;
import java.util.List;

public class WebCrawler {
    private final List<String> articleUrls = new ArrayList<>();
    private final String url = "https://www.reuters.com/world/";
    private final String driverPath = "C:\\Users\\sundb\\WebstormProjects\\BacBackend\\chromedriver.exe";

    public List<String> startCrawling(int maxArticles) {

        System.setProperty("webdriver.chrome.driver", driverPath);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
            options.addArguments("--headless=new");
            WebDriver driver = new ChromeDriver(options);
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
            List<WebElement> articles = driver.findElements(By.cssSelector("h3 a"));

            if (index < articles.size()) {
                String articleUrl = articles.get(index).getAttribute("href");

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





