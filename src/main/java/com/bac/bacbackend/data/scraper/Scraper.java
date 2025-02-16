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
    private static final ThreadLocal<WebDriver> localDriver = new ThreadLocal<>();

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
        localDriver.set(driver);

        try {
            String threadName = Thread.currentThread().getName();
            System.out.println("[" + threadName + "] Fetching from " + url);
            driver.get(url);

            if (Thread.currentThread().isInterrupted()) {
                System.err.println("[" + threadName + "] failed fetching from " + url);
                driver.close();
                driver.quit();
                return;
            }

            String title = getElementText(driver, titleHeader);
            String summary = getElementText(driver, sumBody);;
            System.out.println("[" + threadName + "] summary: " + summary);

            if (summary == null) {
                summary = getElementText(driver, "p");
                if (summary == null) {
                    System.err.println("[" + threadName + "] No summary found");
                }
                return;
            }

            if (summary.length() > 400) summary = summary.substring(0, 400) + "...";

            System.out.println("summary: " + summary);
            System.out.println("title: " + title);

            String[] res = ai.prompt(command, title + " " + summary).split("/");
            if (!(res.length == 6)) {
                System.err.println("[" + threadName + "] " + "Prompt gone wrong, expected 6 output but only got: " + res.length);
                System.err.println(ai.prompt(command, summary));
                return;
            }

            String city = res[0], country = res[1], continent = res[2],
                    category = res[3], x = res[4], y = res[5];

            String sourceName = regex.urlName(url);
            String imgLink = regex.imageSrc(imgUrl);

            if (imgLink == null) {
                WebElement redoImg = driver.findElement(By.cssSelector("img"));
                String resImg = redoImg.getAttribute("src");
                imgLink = regex.imageSrc(resImg);
            }

            Article a = new Article();
            a.setTitle(title);
            a.setSummary(summary);
            a.setId(url);
            a.setSourceName(sourceName);
            a.setCity(city);
            a.setCountry(country);
            a.setContinent(continent);
            a.setCategory(category);
            a.setX(x);
            a.setY(y);
            a.setImgUrl(imgLink);

            aRepo.save(a);
            System.out.println("[" + threadName + "] Saving the article in db with the ID: " + a.getId());
            driver.quit();
        }
        catch (Exception e) {
            String threadName = Thread.currentThread().getName();
            System.err.println("[" + threadName + "] Failed to extract info from: " + url + ": " + e.getMessage());
            driver.close();
            driver.quit();
        }
        finally {
            WebDriver ld = localDriver.get();
            if (ld != null) {
                ld.quit();
                localDriver.remove();
            }
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

            return null;
        } catch (Exception e) {
            return null;
        }
    }

}