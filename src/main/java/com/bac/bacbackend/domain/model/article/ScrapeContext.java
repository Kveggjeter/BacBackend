package com.bac.bacbackend.domain.model.article;

import com.bac.bacbackend.domain.model.scraper.ArticleUrls;
import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScrapeContext {
    private final ScrapingProperties scrapingProperties;
    private String articleUrl;
    private String imgUrl;
    private String title;
    private String summary;
    private String city;
    private String country;
    private String continent;
    private String category;
    private String x;
    private String y;
    private String sourceName;

    public ScrapeContext(ArticleUrls articleUrls, ScrapingProperties scrapingProperties) {
        this.articleUrl = articleUrls.articleUrl();
        this.imgUrl = articleUrls.imgUrl();
        this.scrapingProperties = scrapingProperties;
    }

    public Article toArticle() {
        return new Article(
                articleUrl, sourceName, title, summary,
                city, country, continent,
                category, x, y, imgUrl
        );
    }
}

