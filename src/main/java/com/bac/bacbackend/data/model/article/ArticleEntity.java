package com.bac.bacbackend.data.model.article;

import com.bac.bacbackend.domain.model.article.Article;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Redis entity class for Article. Uses the article url as ID, as Redis can only index with RedisHash.
 * The url is the only thing we really need to get with a time complexity of O(1), since we use it to control the
 * existence of that url in our database. Very convenient library {@link org.springframework.data.redis.core.RedisHash}
 * that also gives us the possibility of deciding the longevity of each article. That way we don't need to make
 * a method that deleted them, they already have a lifespan after creation. Currently, lifetime is at 48hours, but
 * that can easily be changed with changing the timeToLive attribute. I have saved the formula for other options
 * here:
 * <p>
 * 24h = 86400
 * 48h = 172800
 * 72h = 259200
 */
@Getter
@Setter
@RedisHash(value = "Article", timeToLive = 172800)
public class ArticleEntity {

    @Id
    private String id;;
    private String sourceName;
    private String title;
    private String summary;
    private String city;
    private String country;
    private String continent;
    private String category;
    private String x;
    private String y;
    private String imgUrl;

    public ArticleEntity(String id, String sourceName, String title, String summary, String city, String country,
                         String continent, String category, String x, String y, String imgUrl) {
        this.id = id;
        this.sourceName = sourceName;
        this.title = title;
        this.summary = summary;
        this.city = city;
        this.country = country;
        this.continent = continent;
        this.category = category;
        this.x = x;
        this.y = y;
        this.imgUrl = imgUrl;

    }

    public Article toDomain() {
        return new Article(
                id, sourceName, title, summary, city, country, continent, category,
                x, y, imgUrl
        );
    }

    public static ArticleEntity fromDomain(Article article) {
        return new ArticleEntity(
                article.id(),
                article.sourceName(),
                article.title(),
                article.summary(),
                article.city(),
                article.country(),
                article.continent(),
                article.category(),
                article.x(),
                article.y(),
                article.imgUrl());
    }
}
