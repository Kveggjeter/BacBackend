package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.data.repository.article.ArticleRepo;
import com.bac.bacbackend.domain.model.article.Article;
import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
import com.bac.bacbackend.data.service.webdriver.Chrome;
import com.bac.bacbackend.data.service.decomp.OpenAi;
import com.bac.bacbackend.domain.common.Regex;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Scraper {
    private final ArticleRepo aSet;
    private final Regex regex;
    private final OpenAi ai;
    private final String command = StringResource.COMMAND.getValue();
    private final Chrome bs;


    @Autowired
    public Scraper(ArticleRepo aSet, Regex regex, OpenAi ai, Chrome bs) {
        this.aSet = aSet;
        this.regex = regex;
        this.ai = ai;
        this.bs = bs;
    }

    /**
     * Remember to remove the prints, only here for debugging.
     */
    public void scrape(String url, String imgUrl, ScrapeProps s) {

        WebDriver driver = bs.create();

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

            String title = getElementText(driver, s.title());
            String summary = getElementText(driver, s.sum());;
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
                    category = res[3];
            float x = 0, y = 0;
            try {
                x = Float.parseFloat(res[4]);
                y = Float.parseFloat(res[5]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.err.println("[" + threadName + "] " + "Prompt gone wrong, coordinates are string");
                driver.close();
                driver.quit();
            }
            String sourceName = regex.urlName(url);
            String imgLink = regex.imageSrc(imgUrl);

            if (imgLink == null) {
                WebElement redoImg = driver.findElement(By.cssSelector("img"));
                String resImg = redoImg.getAttribute("src");
                imgLink = regex.imageSrc(resImg);
            }
            Article a = new Article(url, sourceName, title, summary, city, country, continent, category, String.valueOf(x), String.valueOf(y), imgLink);
            aSet.adder(a);

            System.out.println("[" + threadName + "] Saving the article in db with the ID: " + url);
            driver.quit();
        }
        catch (Exception e) {
            String threadName = Thread.currentThread().getName();
            System.err.println("[" + threadName + "] Failed to extract info from: " + url + ": " + e.getMessage());
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

            return null;
        } catch (Exception e) {
            return null;
        }
    }

}