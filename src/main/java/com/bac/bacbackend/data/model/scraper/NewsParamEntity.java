package com.bac.bacbackend.data.model.scraper;

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

    public NewsParamEntity() {}
}
