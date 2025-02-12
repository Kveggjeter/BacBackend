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
    private final String command = StringResource.COMMAND.getValue();

    @Autowired
    public Scraper(ArticleRepository aRepo, Regex regex, OpenAi ai) {
        this.aRepo = aRepo;
        this.regex = regex;
        this.ai = ai;
    }

    /**
     * Remember to remove the prints, only here for debugging.
     */
    public void scrape(String url, String imgUrl, String titleHeader, String sumBody) {
        BrowserSettings browserSettings = new BrowserSettings();
        WebDriver driver = browserSettings.driver();

        try {
            String threadName = Thread.currentThread().getName();
            System.out.println("[" + threadName + "] Fetching from " + url);

            driver.get(url);
            String title = getElementText(driver, titleHeader);
            String summary = getElementText(driver, sumBody);;

            String[] res = ai.prompt(command, summary).split("/");
            if (res.length < 7) {
                System.err.println("Prompt gone wrong, expected 7 output but only got: " + res.length);
                System.err.println(ai.prompt(command, summary));
                return;
            }

            String city = res[0], country = res[1], region = res[2],
                    continent = res[3], category = res[4], x = res[5], y = res[6];

            String sourceName = regex.urlName(url);
            String imgLink    = regex.imageSrc(imgUrl);

            System.out.println("[" + threadName + "] Article-Title: " + title);
            System.out.println("[" + threadName + "] Article-Summary: " + summary);
            System.out.println("[" + threadName + "] Article-Source: " + sourceName);
            System.out.println("[" + threadName + "] Article-City: " + city);
            System.out.println("[" + threadName + "] Article-Country: " + country);
            System.out.println("[" + threadName + "] Article-Continent: " + continent);
            System.out.println("[" + threadName + "] Article-Region: " + region);
            System.out.println("[" + threadName + "] Article-x: " + x);
            System.out.println("[" + threadName + "] Article-y: " + y);
            System.out.println("[" + threadName + "] Article-Image: " + imgLink);
            System.out.println("[" + threadName + "] Article-Category: " + category);

            Article a = new Article();
            a.setTitle(title);
            a.setSummary(summary);
            a.setId(url);
            a.setSourceName(sourceName);
            a.setCity(city);
            a.setCountry(country);
            a.setContinent(continent);
            a.setRegion(region);
            a.setCategory(category);
            a.setX(x);
            a.setY(y);
            a.setImgUrl(imgLink);

            aRepo.save(a);
            System.out.println("[" + threadName + "] Saving the article in db with the ID: " + a.getId());
        }
        catch (Exception e) {
            String threadName = Thread.currentThread().getName();
            System.err.println("[" + threadName + "] Failed to extract info from: " + url + ": " + e.getMessage());
        }
        finally {
            driver.close();
            driver.quit();
        }
    }

    /**
     * Priorities on choosing text, but for further generic scanning this could probably be a decent solution to get behind the js-barrier.
     */
    private String getElementText(WebDriver driver, String cssSelector) {
        try {
            WebElement element = driver.findElement(By.cssSelector(cssSelector));
            String text = element.getText();
            if (!text.trim().isEmpty()) {
                return text;
            }

            String content = element.getAttribute("content");
            if (content != null && !content.trim().isEmpty()) {
                return content;
            }

            return "No text or content found";
        } catch (Exception e) {
            return null;
        }
    }

}