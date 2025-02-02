package com.bac.bacbackend.datasource;

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
    private static final List<String> articleUrls = new ArrayList<>();
    private static int index = 0;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sundb\\WebstormProjects\\BacBackend\\chromedriver.exe");

        List<String> latestArticles = crawler(new ChromeDriver());

        System.out.println("\n 18 artikler hentet:");
        for (String url : latestArticles) {
            System.out.println(url);
        }
    }

    public static List<String> crawler(WebDriver driver) {
        if (articleUrls.size() >= 18) {
            driver.quit();
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
