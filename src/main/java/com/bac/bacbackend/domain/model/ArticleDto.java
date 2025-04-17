package com.bac.bacbackend.domain.model;

public record ArticleDto(
        String id,
        String sourceName,
        String title,
        String summary,
        String city,
        String country,
        String region,
        String continent,
        String category,
        String x,
        String y,
        String imgUrl,
        String ldt
) {}
