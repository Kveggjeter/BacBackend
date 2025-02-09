package com.bac.bacbackend.data.scraper;

import com.bac.bacbackend.domain.model.Article;
import com.bac.bacbackend.data.repository.ArticleRepository;
import com.bac.bacbackend.domain.util.Regex;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class Scraper implements Runnable {
    private static final int threadCount = 3;
    private String url;
    private String imgUrl;
    private final ArticleRepository aRepo;
    private final Regex regex;
    private final Article a = new Article();

    @Autowired
    private Scraper(ArticleRepository aRepo, Regex regex) {
        this.aRepo = aRepo;
        this.regex = regex;
    }

    private Scraper(String url, String imgUrl, ArticleRepository aRepo, Regex regex) {
        this.url = url;
        this.imgUrl = imgUrl;
        this.aRepo = aRepo;
        this.regex = regex;
    }

    public void startScraping(String url, String imgUrl) {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        executor.submit(new Scraper(url, imgUrl, aRepo, regex));
        executor.shutdown();

        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) executor.shutdownNow();
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    private void scrape(String threadName) {
        BrowserSettings browserSettings = new BrowserSettings();
        WebDriver driver = browserSettings.driver();

        try {
            System.out.println("[" + threadName + "] Fetching from " + url);
            driver.get(url);
            WebElement titleElement = driver.findElement(By.cssSelector("h1"));
            WebElement summaryElement = driver.findElement(By.cssSelector("div[data-testid^='paragraph-0']"));
            String title = titleElement.getText();
            String summary = summaryElement.getText();
            String city = regex.cityName(summary);
            String sourceName = regex.urlName(url);
            String imgLink = regex.imageSrc(imgUrl);
            System.out.println("[" + threadName + "] Article-Title: " + title);
            System.out.println("[" + threadName + "] Article-Summary: " + summary);
            System.out.println("[" + threadName + "] Article-Source: " + sourceName);
            System.out.println("[" + threadName + "] Article-City: " + city);
            System.out.println("[" + threadName + "] Article-Image: " + imgLink);
            a.setTitle(title);
            a.setSummary(summary);
            a.setId(url);
            a.setSourceName(sourceName);
            a.setCity(city);
            a.setImgUrl(imgLink);

            aRepo.save(a);
            System.out.println("[" + threadName + "] Saving the article in db with the ID: " + a.getId());
        } catch (Exception e) {
            System.err.println("[" + threadName + "] Failed to extract info from: " + url + ": " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    @Override
    public void run() {
        scrape(Thread.currentThread().getName());
    }
}