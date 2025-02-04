package com.bac.bacbackend.data.datasource.scraperUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Scraper implements Runnable {
    private static final String driverPath = "C:\\Users\\sundb\\WebstormProjects\\BacBackend\\chromedriver.exe";
    private final String url;

    static {
        System.setProperty("webdriver.chrome.driver", driverPath);
    }

    public Scraper(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        scrape(threadName);
    }

    private void scrape(String threadName) {
        BrowserSettings browserSettings = new BrowserSettings();
        WebDriver driver = browserSettings.driver();

        try {
            System.out.println("[" + threadName + "] Henter data fra: " + url);
            driver.get(url);
            WebElement titleElement = driver.findElement(By.cssSelector("h1"));
            String title = titleElement.getText();
            System.out.println("[" + threadName + "] Artikkel-tittel: " + title);
        } catch (Exception e) {
            System.err.println("[" + threadName + "] Feil ved scraping av " + url + ": " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
