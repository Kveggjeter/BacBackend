package com.bac.bacbackend.data.scraper;

import com.bac.bacbackend.domain.model.Article;
import com.bac.bacbackend.data.repository.ArticleRepository;
import com.bac.bacbackend.domain.model.StringResource;
import com.bac.bacbackend.domain.service.OpenAi;
import com.bac.bacbackend.domain.util.Regex;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Scraper {

    private final ArticleRepository aRepo;
    private final Regex regex;
    private final OpenAi ai;
    private final String coCity = StringResource.COCITY.getValue();
    private final String coCategory = StringResource.COCATEGORY.getValue();
    private final String te = "h1";
    private final String se = "div[data-testid^='paragraph-0']";

    @Autowired
    public Scraper(ArticleRepository aRepo, Regex regex, OpenAi ai) {
        this.aRepo = aRepo;
        this.regex = regex;
        this.ai = ai;
    }

    /**
     * Remember to remove the prints, only here for debugging.
     */
    public void scrape(String url, String imgUrl) {
        BrowserSettings browserSettings = new BrowserSettings();
        WebDriver driver = browserSettings.driver();

        try {
            String threadName = Thread.currentThread().getName();
            System.out.println("[" + threadName + "] Fetching from " + url);

            driver.get(url);
            WebElement titleElement = driver.findElement(By.cssSelector(te));
            WebElement summaryElement = driver.findElement(By.cssSelector(se));

            String title   = titleElement.getText();
            String summary = summaryElement.getText();
            String city    = ai.prompt(coCity, summary);
            String category = ai.prompt(coCategory, summary);

            String sourceName = regex.urlName(url);
            String imgLink    = regex.imageSrc(imgUrl);

            System.out.println("[" + threadName + "] Article-Title: " + title);
            System.out.println("[" + threadName + "] Article-Summary: " + summary);
            System.out.println("[" + threadName + "] Article-Source: " + sourceName);
            System.out.println("[" + threadName + "] Article-City: " + city);
            System.out.println("[" + threadName + "] Article-Image: " + imgLink);
            System.out.println("[" + threadName + "] Article-Category: " + category);

            Article a = new Article();
            a.setTitle(title);
            a.setSummary(summary);
            a.setId(url);
            a.setSourceName(sourceName);
            a.setCity(city);
            a.setCategory(category);
            a.setImgUrl(imgLink);

            aRepo.save(a);
            System.out.println("[" + threadName + "] Saving the article in db with the ID: " + a.getId());
        }
        catch (Exception e) {
            String threadName = Thread.currentThread().getName();
            System.err.println("[" + threadName + "] Failed to extract info from: " + url + ": " + e.getMessage());
        }
        finally {
            driver.quit();
        }
    }
}