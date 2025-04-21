package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
import com.bac.bacbackend.domain.port.*;
import org.openqa.selenium.StaleElementReferenceException;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Crawler gives urls and gives back ArticleUrls containing urls from thumbnail picture and the url for
 * further crawling.
 */
@Component
public class WebCrawler extends CrawlerSetup<ArticleUrls> {

    private final INewsParamRepo newsParamRepo;
    private final IArticleRepo articleRepo;
    private final IFailedRepo failedRepo;
    private ScrapeProps scrapeProps;

    private String txtLocator;
    private String imgLocator;
    private String imgHref;


    public WebCrawler(IArticleRepo articleRepo, IWebSelectors webSelectors, IChrome browser, INewsParamRepo newsParamRepo, IFailedRepo failedRepo) {
        super(browser, webSelectors);
        this.newsParamRepo = newsParamRepo;
        this.articleRepo = articleRepo;
        this.failedRepo = failedRepo;
    }

    /**
     * Starts the crawl and returns the collected data as a List for scraping
     * @param propertyIndex index of webelements used for crawling
     * @param maxArticles max amount of articles we want to save
     * @return a list with ArticleUrls
     */
    public List<ArticleUrls> crawl(int propertyIndex, int maxArticles) {
        return doCrawl(propertyIndex, maxArticles);
    }

    /**
     * Setting the properties of each web-selector. It is used in an iteration, so the
     * propertyIndex changes for each run.
     *
     * @param propertyIndex index for what properties the crawler should use
     */
    protected void setProperties(int propertyIndex) {
        setScrapeProps(newsParamRepo.select(propertyIndex));
        startUrl = scrapeProps.url();
        txtLocator = scrapeProps.txtLocator();
        imgLocator = scrapeProps.imgLocator();
        imgHref = scrapeProps.imgHref();
    }

    /**
     * Overrides parent class. Crawls through the starting url, collecting further urls along
     * the way. The checks are primarily based on the size of the iteration, as the iteration
     * will continue until it either has reached the max amount of articles or collected
     * all the articles on a given page.
     *
     * @param maxArticles max amount of articles allowed saved for the session
     */
    @Override
    protected void startCrawling(int maxArticles) {

        try {
            List<String> urls = webSelectors.values(txtLocator);
            List<String> imgUrls = webSelectors.values(imgLocator, imgHref);
            if (maxArticles > urls.size()) maxArticles = urls.size();

            int maxListSize;
            if (urls.size() > imgUrls.size()) maxListSize = imgUrls.size();
            else if (urls.size() < imgUrls.size()) maxListSize = maxArticles;
            else maxListSize = urls.size();

            int count = 0;
            int i = 0;
            while (count < maxArticles && i < maxListSize) {
                System.out.println("Count: " + count);
                System.out.println("Iteration: " + i);
                if(i > urls.size()) return;
                System.out.println(urls.get(i));
                System.out.println(imgUrls.get(i));
                boolean ok = addArticle(urls.get(i), imgUrls.get(i));
                i++;
                if (ok) count++;
            }

        } catch (StaleElementReferenceException e) {
            System.out.println("Something went wrong while craling: " + e.getMessage());
        }
    }

    /**
     * Checks if an article can be added. If false, the iteration will continue.
     * It checks if the article already has been added both to the list
     * and in the db. It also checks if it has been failed previously.
     *
     * @param articleUrl collected articleUrl from crawled site
     * @param imgUrl collected imgUrl from crawled site
     * @return boolean check if the article can be added
     */
    private boolean addArticle(String articleUrl, String imgUrl) {
        if(articleUrl != null
                && !articleRepo.exists(articleUrl)
                && !failedRepo.exists(articleUrl)
                && !list.contains(new ArticleUrls(articleUrl, imgUrl))) {
            list.add(new ArticleUrls(articleUrl, imgUrl));
            System.out.println("Fetched article #" + list.size() + ": " + articleUrl);
            System.out.println("Fetched img #" + list.size() + ": " + imgUrl);
            return true;
        }
        return false;
    }

    /**
     * Setting the global scraper property.
     *
     * @param scrape the chosen scraper properties
     */
    private void setScrapeProps(ScrapeProps scrape) {
        scrapeProps = scrape;
    }

}
