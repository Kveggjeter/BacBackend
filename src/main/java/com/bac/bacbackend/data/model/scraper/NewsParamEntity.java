package com.bac.bacbackend.data.model.scraper;

import com.bac.bacbackend.domain.model.scraper.ScrapingProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Redis Entity class for webelement properties used for scraping.
 */
@Getter
@Setter
@RedisHash(value = "NewSource")
public class NewsParamEntity {

    @Id
    private String url;
    private String txtLocator;
    private String txtHref;
    private String imgLocator;
    private String imgHref;
    private String title;
    private String sum;

    public NewsParamEntity(
            String url, String txtLocator, String txtHref, String imgLocator, String imgHref, String title, String sum

    ) {
        this.url = url;
        this.txtLocator = txtLocator;
        this.txtHref = txtHref;
        this.imgLocator = imgLocator;
        this.imgHref = imgHref;
        this.title = title;
        this.sum = sum;
    }

    public ScrapingProperties toDomain() {
        return new ScrapingProperties(
                url, txtLocator, txtHref, imgLocator, imgHref, title, sum
        );
    }

    public static NewsParamEntity fromPostgres(ScraperObjectEntity so) {
        return new NewsParamEntity(
                so.getUrl(),
                so.getTxtLocator(),
                so.getTxtHref(),
                so.getImgLocator(),
                so.getImgHref(),
                so.getTitle(),
                so.getSum()
        );
    }
}
