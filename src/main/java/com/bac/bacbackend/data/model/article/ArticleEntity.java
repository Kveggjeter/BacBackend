package com.bac.bacbackend.data.model.article;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 *
 * Getter/setter source: https://www.baeldung.com/lombok-omit-getter-setter
 *
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

    public ArticleEntity() {}

}
