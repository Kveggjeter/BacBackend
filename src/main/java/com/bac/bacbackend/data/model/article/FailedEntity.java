package com.bac.bacbackend.data.model.article;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Redis entity for urls that the scraper has failed on multiple times.
 * Follows the same story as {@link ArticleEntity}, where we index the url to get quick access when checking for
 * existence of the url we are trying to scrape. {@link org.springframework.data.redis.core.RedisHash} does a lot of
 * legwork for us here, time to live for each instance is put down to 48 hours but can easily be changed with changing
 * the timeToLive attribute. I have saved the formula for other options here:
 * <p>
 * 24h = 86400
 * 48h = 172800
 * 72h = 259200
 */
@Getter
@Setter
@RedisHash(value = "Failed", timeToLive = 172800)
public class FailedEntity {

    @Id
    private String url;

    public FailedEntity() {}
}
