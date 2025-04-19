package com.bac.bacbackend.domain.service.scraper;

import com.bac.bacbackend.domain.model.article.Article;
import com.bac.bacbackend.domain.model.scraper.ArticleData;
import com.bac.bacbackend.domain.model.scraper.ScrapeProps;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScrapeContext {
    private final String url;
    private String imgUrl;
    private final ScrapeProps sp;
    private String title;
    private String summary;
    private String city;
    private String country;
    private String continent;
    private String category;
    private String x;
    private String y;
    private String sourceName;

    public ScrapeContext(ArticleData data, ScrapeProps sp) {
        this.url    = data.articleUrl();
        this.imgUrl = data.imgUrl();
        this.sp  = sp;
    }

    public Article toArticle() {
        return new Article(
                url, sourceName, title, summary,
                city, country, continent,
                category, x, y, imgUrl
        );
    }
}

