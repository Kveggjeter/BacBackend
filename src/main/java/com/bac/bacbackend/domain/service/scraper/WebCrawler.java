package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.model.scraper.ArticleData;
import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
import com.bac.bacbackend.domain.port.*;
import org.openqa.selenium.StaleElementReferenceException;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class WebCrawler extends Spider<ArticleData> {

    private final INewsParamRepo nRepo;
    private final IArticleRepo repo;
    private final IFailedRepo fRepo;
    private ScrapeProps sp;

    private String txtLocator;
    private String imgLocator;
    private String imgHref;


    public WebCrawler(IArticleRepo repo, ICrawler cr, IChrome browser, INewsParamRepo nRepo, IFailedRepo fRepo) {
        super(browser, cr);
        this.nRepo = nRepo;
        this.repo = repo;
        this.fRepo = fRepo;
    }

    public List<ArticleData> crawl(int n, int max) {
        return doCrawl(n, max);
    }

    protected void propsSetter(int n) {
        setSp(nRepo.select(n));
        startUrl = sp.url();
        txtLocator = sp.txtLocator();
        imgLocator = sp.imgLocator();
        imgHref = sp.imgHref();
    }

    @Override
    protected void startCrawling(int max) {

        try {
            List<String> urls = cr.values(txtLocator);
            List<String> imgUrl = cr.values(imgLocator, imgHref);
            if (max > urls.size()) max = urls.size();

            int maxListSize;
            if (urls.size() > imgUrl.size()) maxListSize = imgUrl.size();
            else if (urls.size() < imgUrl.size()) maxListSize = max;
            else maxListSize = urls.size();

            int count = 0;
            int i = 0;
            while (count < max && i < maxListSize) {
                System.out.println("Count: " + count);
                System.out.println("Iteration: " + i);
                if(i > urls.size()) return;
                System.out.println(urls.get(i));
                System.out.println(imgUrl.get(i));
                boolean ok = addArticle(urls.get(i), imgUrl.get(i));
                i++;
                if (ok) count++;
            }

        } catch (StaleElementReferenceException e) {
            System.out.println("Something went wrong while craling: " + e.getMessage());
        }
    }

    private boolean addArticle(String url, String imgUrl) {
        if(url != null && !repo.exists(url)
                && !fRepo.exists(url)
                && !list.contains(new ArticleData(url, imgUrl))) {
            list.add(new ArticleData(url, imgUrl));
            System.out.println("Fetched article #" + list.size() + ": " + url);
            System.out.println("Fetched img #" + list.size() + ": " + imgUrl);
            return true;
        }
        return false;
    }

    private void setSp(ScrapeProps scrape) {
        sp = scrape;
    }

}
