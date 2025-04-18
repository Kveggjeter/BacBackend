package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.model.scraper.ArticleData;
import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
import com.bac.bacbackend.domain.port.IArticleRepo;

import java.util.ArrayList;

public abstract class Spider {
    protected ScrapeProps sp;
    private final IArticleRepo repo;
    protected int maxNum;
    protected ArrayList<ArticleData> list;

    public Spider(
            IArticleRepo repo
    ){
        this.repo = repo;
    }

    protected void setMaxNum(int n) { maxNum = n; }

    protected void setSp(ScrapeProps scrape) {
        sp = scrape;
    }

    protected void useList() {
        if (list == null)
            list = new ArrayList<>();
    }

    protected int size() {
        return list.size();
    }

    protected void addArticle(String url, String imgUrl) {
        useList();
        if(url != null && !repo.exists(url)
        && !list.contains(new ArticleData(url, imgUrl))) {
            list.add(new ArticleData(url, imgUrl));
            System.out.println("Fetched article #" + size() + ": " + url);
            System.out.println("Fetched img #" + list.size() + ": " + imgUrl);
        }
    }

}
