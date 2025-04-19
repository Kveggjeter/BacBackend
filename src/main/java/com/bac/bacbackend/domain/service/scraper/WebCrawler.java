package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.model.scraper.ArticleData;
import com.bac.bacbackend.domain.port.IChrome;
import com.bac.bacbackend.domain.port.INewsParamRepo;
import com.bac.bacbackend.domain.port.IArticleRepo;
import com.bac.bacbackend.domain.port.ICrawler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class WebCrawler extends Spider {

    private final ICrawler cr;
    private final IChrome browser;
    private final INewsParamRepo nRepo;
    private String txtLocator;
    private String txtHref;
    private String imgLocator;
    private String imgHref;


    public WebCrawler(IArticleRepo repo, ICrawler cr, IChrome browser, INewsParamRepo nRepo) {
        super(repo);
        this.cr = cr;
        this.browser = browser;
        this.nRepo = nRepo;
    }

    public ArrayList<ArticleData> crawl(int n, int max) {
        if(list !=null)
            list.clear();
        useList();
        propsSetter(n);
        setMaxNum(max);
        browser.start(sp.url());

        try {
            System.out.println("Crawling webpage, please wait...");
            startCrawling(0);
        } finally {
            browser.stop();
        }
        System.out.println("Finito buddy");
        return list;
    }

    private void propsSetter(int n) {
        setSp(nRepo.select(n));
        txtLocator = sp.txtLocator();
        txtHref = sp.txtHref();
        imgLocator = sp.imgLocator();
        imgHref = sp.imgHref();
    }

    public void startCrawling(int n) {
        if (size() >= maxNum) return;
        try {
            cr.content(n);
            String url = cr.values(txtHref, txtLocator);
            String imgUrl = cr.values(imgHref, imgLocator);

            addArticle(url, imgUrl);

            startCrawling(n + 1);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
