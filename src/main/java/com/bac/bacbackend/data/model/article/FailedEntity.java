package com.bac.bacbackend.data.model.article;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Redis entity for urls that the scraper has failed on multiple times.
 */
@Getter
@Setter
@RedisHash(value = "Failed", timeToLive = 172800)
public class FailedEntity {

    @Id
    private String url;

    public FailedEntity() {}
}
