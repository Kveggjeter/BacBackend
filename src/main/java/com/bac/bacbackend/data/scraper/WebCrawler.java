package com.bac.bacbackend.data.scraper;

import com.bac.bacbackend.data.repository.ArticleRepository;
import com.bac.bacbackend.data.scraper.config.WebGetter;
import com.bac.bacbackend.domain.model.ArticleData;
import com.bac.bacbackend.domain.model.SourceDto;
import com.bac.bacbackend.domain.service.BrowserSettings;
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
    private final WebGetter wbs;
    private final BrowserSettings bs;

    public WebCrawler(ArticleRepository aRepo, WebGetter wbs, BrowserSettings bs) {
        this.aRepo = aRepo;
        this.wbs = wbs;
        this.bs = bs;
    }

    public ArrayList<ArticleData> startCrawling(int n, int maxNum) {

        ArrayList<ArticleData> articlesList = new ArrayList<>();
        SourceDto s = wbs.selectors(n);
        WebDriver driver = bs.create();
        try {
            driver.get(s.url());
            System.out.println("Crawling webpage, please wait...");
            crawl(0, driver, maxNum, s, articlesList);

            int count = articlesList.size();
            System.out.println("Crawling finished. Managed to get " + count + " amount of articles");

        } finally {
            driver.quit();
        }
        return articlesList;
    }

    private ArrayList<ArticleData> crawl(
            int index,
            WebDriver driver,
            int maxNum,
            SourceDto s,
            ArrayList<ArticleData> list) {
        if (list.size() >= maxNum) return list;

        try {
            List<WebElement> articles = driver.findElements(By.cssSelector(s.txtLocator()));
            List<WebElement> img = driver.findElements(By.cssSelector(s.imgLocator()));

            if (index < articles.size()) {
                String url = articles.get(index).getAttribute(s.txtHref());
                String imgUrl = (index < img.size()) ? img.get(index).getAttribute(s.imgHref()) : "NO_IMAGE";

                    if(
                        url != null
                        && !aRepo.existsById(url)
                        && !list.contains(new ArticleData(url, imgUrl))
                    ){
                            list.add(new ArticleData(url, imgUrl));
                            System.out.println("Fetched article #" + list.size() + ": " + url);
                            System.out.println("Fetched img #" + list.size() + ": " + imgUrl);
                        }

                return crawl(index + 1, driver, maxNum, s, list);
            }
        } catch (Exception e) {
            System.out.println("Error while fetching articles: " + e.getMessage());
        }
        return list;
    }


}



