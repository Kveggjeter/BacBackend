package com.bac.bacbackend.domain.model.article;

import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
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

    public ScrapeContext(ArticleUrls data, ScrapeProps sp) {
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

