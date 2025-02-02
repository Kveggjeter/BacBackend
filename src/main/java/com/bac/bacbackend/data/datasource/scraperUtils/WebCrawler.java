package com.bac.bacbackend.data.datasource.scraperUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WebCrawler {
    private final List<String> articleUrls = new ArrayList<>();
    private int index = 0;

    public List<String> startCrawling() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sundb\\WebstormProjects\\BacBackend\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        List<String> articles = crawler(driver);

        driver.quit();
        return articles;
    }

    private List<String> crawler(WebDriver driver) {
        if (articleUrls.size() >= 18) {
            return articleUrls;
        }

        driver.get("https://www.reuters.com/world/");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            List<WebElement> articles = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".story-card a")));

            if (index < articles.size()) {
                String articleUrl = articles.get(index).getAttribute("href");

                if (!articleUrls.contains(articleUrl)) {
                    articleUrls.add(articleUrl);
                    System.out.println("Hentet artikkel #" + articleUrls.size() + ": " + articleUrl);
                }

                index++;
                return crawler(driver);
            }

        } catch (Exception e) {
            System.out.println("Feil ved scraping: " + e.getMessage());
        }

        return articleUrls;
    }
}

