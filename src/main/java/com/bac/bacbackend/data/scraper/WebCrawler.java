package com.bac.bacbackend.data.scraper;

import com.bac.bacbackend.data.repository.ArticleRepository;
import com.bac.bacbackend.data.scraper.config.WebSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Component
public class WebCrawler {

    @Autowired
    private ArticleRepository aRepo;
    private final WebSetter wbs;

    public WebCrawler(WebSetter wbs) {
        this.wbs = wbs;
    }

    /**
     * Running this each time necessary.
     * @param n
     * @param maxNum
     * @return
     */
    public ArrayList<ArticleData> startCrawling(int n, int maxNum) {

        ArrayList<ArticleData> articlesList = new ArrayList<>();
        ArrayList<String> source = wbs.make(n);
        BrowserSettings bs = new BrowserSettings();
        WebDriver driver = bs.driver();

        System.out.println("Crawling webpage, please wait...");

        driver.get(source.get(0));
        ArrayList<ArticleData> articles = crawl(0, driver, maxNum, source, articlesList);
        driver.quit();

        int count = articlesList.size();
        System.out.println("Crawling finished. Managed to get " + count + " amount of articles");
        return articles;
    }

    /**
     * We've already found most of the actual info. Scraper is more for location and categorization.
     * @param index
     * @param driver
     * @param maxNum
     * @param source
     * @param articlesList
     * @return
     */
    private ArrayList<ArticleData> crawl(int index, WebDriver driver, int maxNum, ArrayList<String> source, ArrayList<ArticleData> articlesList) {
        if (articlesList.size() >= maxNum) return articlesList;

        try {
            List<WebElement> articles = driver.findElements(By.cssSelector(source.get(1)));
            List<WebElement> img = driver.findElements(By.cssSelector(source.get(3)));

            if (index < articles.size()) {
                String url = articles.get(index).getAttribute(source.get(2));
                String imgUrl = (index < img.size()) ? img.get(index).getAttribute(source.get(4)) : "NO_IMAGE";
                assert url != null;

                if(!aRepo.existsById(url) && !articlesList.contains(new ArticleData(url, imgUrl))){
                    articlesList.add(new ArticleData(url, imgUrl));

                    System.out.println("Fetched article #" + articlesList.size() + ": " + url);
                    System.out.println("Fetched img #" + articlesList.size() + ": " + imgUrl);
                }
                return crawl(index + 1, driver, maxNum, source, articlesList);
            }
        } catch (Exception e) {
            System.out.println("Error while fetching articles: " + e.getMessage());
        }
        return articlesList;
    }

    public record ArticleData(String articleUrl, String imgUrl) {}
}



