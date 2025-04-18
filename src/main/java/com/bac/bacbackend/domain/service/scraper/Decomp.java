package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.model.article.Article;
import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
import com.bac.bacbackend.domain.port.IArticleRepo;

public abstract class Decomp {

    protected ScrapeProps sp;
    private final IArticleRepo repo;
    protected String url;
    protected String sourceName;
    protected String title;
    protected String summary;
    protected String city;
    protected String country;
    protected String continent;
    protected String category;
    protected String x;
    protected String y;
    protected String imgUrl;
    protected Article a;

    public Decomp(IArticleRepo repo) { this.repo = repo;}

    protected void setSp(ScrapeProps scrape) { sp = scrape; }

    protected void setArticle() {
        try {
            a = new Article(url, sourceName, title, summary, city, country, continent, category, x, y, imgUrl);
            saveArticle();
        } catch (Exception e) {
            System.err.println("Could not make a new article, data is corrupt : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void saveArticle() {
        try {
            repo.adder(a);
        } catch (Exception e) {
            System.err.println("Could not make save the article, object corrupt : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}



//
//    protected void setUrl(String s) {
//        this.url = s;
//    }
//
//    protected void setSourceName(String s) {
//        this.sourceName = s;
//    }
//
//    protected void setTitle(String s) {
//        this.title = s;
//    }
//
//    protected void setSummary(String s) {
//        this.summary = s;
//    }
//
//    protected void setCity(String s) {
//        this.city = s;
//    }
//
//    protected void setCountry(String s) {
//        this.country = s;
//    }
//
//    protected void setContinent(String s) {
//        this.continent = s;
//    }
//
//    protected void setCategory(String s) {
//        this.category = s;
//    }
//
//    protected void setX(String s) {
//        this.x = s;
//    }
//
//    protected void setY(String s) {
//        this.y = s;
//    }
//
//    protected void setImgUrl(String s) {
//        this.imgUrl = s;
//    }